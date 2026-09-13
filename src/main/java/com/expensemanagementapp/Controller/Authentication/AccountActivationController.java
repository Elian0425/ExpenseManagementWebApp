package com.expensemanagementapp.Controller.Authentication;

import com.expensemanagementapp.Config.ApplicationProperties;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Entity.UserVerificationToken;
import com.expensemanagementapp.Service.EmailSenderService;
import com.expensemanagementapp.Service.UserService;
import com.expensemanagementapp.Service.UserVerificationTokenService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@Log4j2
public class AccountActivationController {


    @Autowired
    private UserVerificationTokenService userVerificationTokenService;

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationProperties applicationProperties;

    @Transactional
    @GetMapping("/activation")
    public String activateAccount(@RequestParam String token, RedirectAttributes redirectAttributes) {

        // Get token
        UserVerificationToken userVerificationToken = userVerificationTokenService.getToken(token);

        // Prepare default notification message and button details for successful activation
        String notificationMessage = "Your account has been successfully activated. You can now log in.";
        String notificationButtonTitle = "Login";
        String notificationButtonLink = "/auth/login";

        // Check if the token is valid
        boolean isTokenValid = userVerificationTokenService.isTokenValid(userVerificationToken);

        // If invalid, prepare a notification message for expired token and redirect to the login page
        if (token == null || !isTokenValid) {
            notificationMessage = "Your account activation link has expired. Please register again to receive a new activation link.";

            // Update the button details for expired token scenario
            redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
            redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
            redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);

            // Redirect to the message page with the appropriate notification
            return "redirect:/message";
        }

        // Get user associated with the token
        User user = userVerificationToken.getUser();

        // Activate the user's account
        userService.accountActivation(user);

        // If the token is valid, proceed with account activation and prepare a notification message for successful activation
        redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
        redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
        redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);

        // Prepare email notification
        String emailTo = user.getUserEmail();
        String emailSubject = "Account Activation - Expense Management App";
        String supportLink = applicationProperties.getBaseUrl() + "/support"; // Replace with your actual support link
        String emailBody =
                "Dear "
                        + user.getUserFirstName()
                        + " " + user.getUserLastName()
                        + ",\n\n"
                        + "Your account is now activated! \n\n"
                        + "You can now log in to your account and start managing your expenses."
                        + "\n\n"
                        + "If you have any questions or need assistance, feel free to contact our support team:\n"
                        + supportLink
                        + "\n\n\n"
                        + "Best regards,\n"
                        + "Expense Management App Team";

        // Send email notification
        emailSenderService.sendEmail(emailTo, emailSubject, emailBody);

        // Redirect to the message page with the appropriate notification
        return "redirect:/message";
    }
}
