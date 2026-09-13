package com.expensemanagementapp.Controller.Authentication;

import com.expensemanagementapp.Config.ApplicationProperties;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Entity.UserVerificationToken;
import com.expensemanagementapp.Model.UserModel;
import com.expensemanagementapp.Service.EmailSenderService;
import com.expensemanagementapp.Service.UserService;
import com.expensemanagementapp.Service.UserVerificationTokenService;
import com.expensemanagementapp.Validation.Group.EmailUpdateValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmailUpdateController {

    @Autowired
    private UserVerificationTokenService userVerificationTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @GetMapping("/update-email")
    public String emailUpdate(UserModel userModel, Model model) {
        model.addAttribute("userModel", userModel);
        return "Credential/Authentication/update-email-form";
    }

    @PostMapping("/update-email")
    public String updateEmail(@Validated(EmailUpdateValidationGroup.class)
                              @ModelAttribute UserModel userModel,
                              BindingResult bindingResult, Model model,
                              RedirectAttributes redirectAttributes,
                              Authentication authentication) {

        // Check for binding result errors
        if (bindingResult.hasErrors()) {
            // Send and populate form
            model.addAttribute("userModel", userModel);
            return "Credential/Authentication/update-email-form";
        }

        // Get user email from authentication token
        String userEmail = authentication.getName();

        // Load user from database using email
        User user = userService.getUserByEmail(userEmail);


        // PREPARE EMAIL

        // Email Notification
        String emailTo = userModel.getUserEmail();
        String emailSubject = "Expense Management App - Email Update Confirmation";


        String token = userVerificationTokenService.generateToken(user, userModel.getUserEmail());
        String verificationLink = applicationProperties.getBaseUrl() + "/update-email/verify?token=" + token;

        String emailBody =
                "Dear, "
                        + userModel.getUserFirstName()
                        + " "
                        + userModel.getUserLastName()
                        + ",\n\n"
                        + "An email address update request has been sent to your email. "
                        + "Please click the following link to confirm your new email address:"
                        + verificationLink
                        + "\n\n"
                        + "If you did not initiate this change, please contact our support team immediately.\n\n"
                        + "Thank you for using our Expense Management App.\n\n" +
                        "Expense Management App Team";

        // Send Email
        emailSenderService.sendEmail(emailTo, emailSubject, emailBody);

        // In App Notification
        String notificationMessage = "A request for an email update has been sent to your new email. Please check for email for instructions.";
        String notificationButtonTitle = "Go to User Profile";
        String notificationButtonLink = "/dashboard/user/profile";

        // Send notification attributes via RedirectAttributes
        redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
        redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
        redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);

        // Display view
        return "redirect:/message";
    }

    @GetMapping("/update-email/verify")
    public String emailUpdateVerification(@RequestParam String token, RedirectAttributes redirectAttributes) {

        String notificationMessage;
        String notificationButtonTitle = "Go to User Profile";
        String notificationButtonLink = "/dashboard/user/profile";

        UserVerificationToken userVerificationToken = userVerificationTokenService.getToken(token);

        boolean isTokenValid = userVerificationTokenService.isTokenValid(userVerificationToken);

        if (!isTokenValid) {
            // Prepare failure notification
            notificationMessage = "The verification token is invalid or has expired. Please request a new email update.";
        } else {
            // Get the user associated with the token
            User user = userVerificationToken.getUser();
            String userNewEmail = userVerificationToken.getUserNewEmail();

            // Update the user's email
            userService.userEmailUpdate(user, userNewEmail);

            // Prepare success notification
            notificationMessage = "Your email has been successfully updated.";


            // Email Notification

            String emailTo = user.getUserEmail();
            String emailSubject = "Expense Management App - Email Update Confirmation";
            String emailBody =
                    "Dear, "
                            + user.getUserFirstName()
                            + " " + user.getUserLastName()
                            + ",\n\n"
                            + "Your email address has been successfully updated." +
                    "Thank you for using our Expense Management App.\n\n" +
                    "Expense Management App Team";

            emailSenderService.sendEmail(emailTo, emailSubject, emailBody);
        }


        // Send notification attributes via RedirectAttributes
        redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
        redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
        redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);


        return "redirect:/message";
    }


}
