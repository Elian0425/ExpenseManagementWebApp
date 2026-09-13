package com.expensemanagementapp.Controller.Authentication;

import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Entity.UserVerificationToken;
import com.expensemanagementapp.Model.UserModel;
import com.expensemanagementapp.Service.EmailSenderService;
import com.expensemanagementapp.Service.UserService;
import com.expensemanagementapp.Service.UserVerificationTokenService;
import com.expensemanagementapp.Validation.Group.PasswordResetValidationGroup;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
public class PasswordResetController {

    @Autowired
    private UserVerificationTokenService userVerificationTokenService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/auth/reset-password", "/reset-password"})
    public String resetPassword(@RequestParam(value = "token", required = false) String token, UserModel userModel,
                                Model model,
                                RedirectAttributes redirectAttributes, Authentication authentication) {

        // Declare variable
        String formAction;
        String redirectUrl;

        // If user NOT authenticated
        if (authentication == null || !authentication.isAuthenticated()) {

            // Get Token
            UserVerificationToken verificationToken = userVerificationTokenService.getToken(token);

            // Check if token is valid
            boolean isVerificationTokenValid = userVerificationTokenService.isTokenValid(verificationToken);

            if (!isVerificationTokenValid) {
                String notificationMessage = "Your password has expired. Please login again.";
                String notificationButtonTitle = "Go to login";
                String notificationButtonLink = "/auth/login";

                redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
                redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
                redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);

                return "redirect:/message";
            }

            // Assign form action and redirect url
            formAction = "/auth/reset-password?token=" + token;
            redirectUrl = "/auth/login";

        } else {
            formAction = "/reset-password";
            redirectUrl = "/dashboard/profile";
        }

        return passwordResetFormPopulation(formAction, redirectUrl, userModel, model);
    }

    @PostMapping(value = {"/auth/reset-password", "/reset-password"})
    public String resetPassword(@RequestParam(value = "token", required = false) String token,
                                @Validated(PasswordResetValidationGroup.class)
                                @ModelAttribute UserModel userModel,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            String formAction = "/auth/reset-password?token=" + token;
            String redirectUrl = "/auth/login";

            if (authentication != null && authentication.isAuthenticated()) {
                formAction = "/reset-password";
                redirectUrl = "/dashboard/user/profile";
            }

            return passwordResetFormPopulation(formAction, redirectUrl, userModel, model);

        }

        // Declare user variable
        User user;

        // Declare in-app notification variables
        String notificationButtonTitle;
        String notificationButtonLink;

        // Get User from token if user is not authenticated, else get user from context
        if (authentication == null || !authentication.isAuthenticated()) {
            UserVerificationToken verificationToken = userVerificationTokenService.getToken(token);
            user = verificationToken.getUser();
            notificationButtonTitle = "Go to login";
            notificationButtonLink = "/auth/login";
        } else{
            String userEmail = authentication.getName();
            user =  userService.getUserByEmail(userEmail);
            notificationButtonTitle = "Go to dashboard";
            notificationButtonLink = "/dashboard/user/profile";
        }

        // Change password
        userService.userPasswordReset(user, userModel);

        // Prepare Email
        String emailTo = user.getUserEmail();
        String emailSubject = "Expense Management App - Password Reset Confirmation";
        String emailBody =
                "Dear "
                        + user.getUserFirstName()
                        + " "
                        + user.getUserLastName()
                        + ",\n\n"
                        + "Your password has been successfully reset."
                        + "If you did not perform this action, please contact our support team immediately.\n\n"
                        + "Best regards,\n"
                        + "Expense Management App Team";

        // Send Email
        emailSenderService.sendEmail(emailTo, emailSubject, emailBody);

        // Prepare in-app notification message
        String notificationMessage = "Your password has been successfully reset.";

        // Send attributes to message page
        redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
        redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
        redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);

        return "redirect:/message";
    }


    private String passwordResetFormPopulation(String formAction, String redirectUrl, UserModel userModel, Model model) {
        model.addAttribute("userModel", userModel);
        model.addAttribute("formAction", formAction);
        model.addAttribute("redirectUrl", redirectUrl);

        return "Credential/Authentication/reset-password-form";
    }

}
