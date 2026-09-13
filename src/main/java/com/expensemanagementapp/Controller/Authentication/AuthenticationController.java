package com.expensemanagementapp.Controller.Authentication;

import com.expensemanagementapp.Config.ApplicationProperties;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.UserModel;
import com.expensemanagementapp.Service.EmailSenderService;
import com.expensemanagementapp.Service.UserService;
import com.expensemanagementapp.Service.UserVerificationTokenService;
import com.expensemanagementapp.Validation.Group.EmailLookUpValidationGroup;
import com.expensemanagementapp.Validation.Group.ForgotPasswordValidationGroup;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@Log4j2
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserVerificationTokenService userVerificationTokenService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private ApplicationProperties applicationProperties;

    @GetMapping("/{authenticationType}")
    public String getAuthenticationPages(@PathVariable String authenticationType, UserModel userModel, Model model, RedirectAttributes redirectAttributes) {

        String formTitle = "Email Lookup";
        String formAction = "/auth/forgot-email";

        if (authenticationType.equals("forgot-password")) {
            formTitle = "Password Reset";
            formAction = "/auth/forgot-password";
        }

        return authenticationFormPopulation(authenticationType, userModel, formTitle, formAction, model);
    }

    @PostMapping("/forgot-email")
    public String postForgotEmail(
            @Validated(EmailLookUpValidationGroup.class)
            @ModelAttribute UserModel userModel, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        // Check for binding result errors
        if (bindingResult.hasErrors()){

            // Set the authentication type, form title, and form action for the model
            String authenticationType = "forgot-email";
            String formTitle = "Email Lookup";
            String formAction = "/auth/forgot-email";

            return authenticationFormPopulation(authenticationType, userModel, formTitle, formAction, model);
        }

        // Verify user, return user object or null
        User user = userService.getUserByFirstnameAndLastnameAndDOBAndPhoneNumber(
                userModel.getUserFirstName(),
                userModel.getUserLastName(),
                userModel.getUserDOB(),
                userModel.getUserPhoneNumber()
        );


        // Set in-app notification message and redirection path
        String notificationMessage = "User not found with the provided information. Please try again.";
        String notificationButtonTitle = "Go Back";
        String notificationButtonLink = "/auth/forgot-email";

        // If user is found, update the notification message and redirection path
        if (user != null) {
            notificationMessage = "Here is the email associated with your account: " + user.getUserEmail();
            notificationButtonTitle = "Go to Login";
            notificationButtonLink = "/auth/login";
        }

        // Redirect values to the message view
        redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
        redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
        redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);

        // Return view
        return "redirect:/message";
    }

    @PostMapping("/forgot-password")
    public String postForgotPassword(@Validated(ForgotPasswordValidationGroup.class) UserModel userModel,
                                     BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        // Check for binding result errors
        if (bindingResult.hasErrors()){

            // Set the authentication type, form title, and form action for the model
            String authenticationType = "forgot-password";
            String formTitle = "Password Reset";
            String formAction = "/auth/forgot-password";

            return  authenticationFormPopulation(authenticationType, userModel, formTitle, formAction, model);
        }

        // Verify user, return user object or null
        User user = userService.getUserByFirstnameAndLastnameAndDOBAndEmailAddress(
                userModel.getUserFirstName(),
                userModel.getUserLastName(),
                userModel.getUserDOB(),
                userModel.getUserEmail()
        );

        // Set in-app notification message and redirection path
        String notificationMessage = "An email has been sent to your account with instruction to reset your password.";
        String notificationButtonTitle = "Go to Login";
        String notificationButtonLink = "/auth/login";

        // If user is not found, update the notification message and redirection path, redirect to message view.
        if (user == null) {

            // Set redirect attributes
            notificationMessage = "User not found with the provided information. Please try again.";
            notificationButtonTitle = "Go Back";
            notificationButtonLink = "/auth/forgot-password";

            // Send redirect attributes to the message view
            redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
            redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
            redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);

            // Return view
            return "redirect:/message";
        }

        // Prepare Email Notification
        String emailTo =  user.getUserEmail();
        String emailSubject = "Expense Management App - Password Reset";

        String token = userVerificationTokenService.generateToken(user);
        String verificationLink = applicationProperties.getBaseUrl() + "/auth/reset-password?token=" + token;

        String emailBody =
                "Dear "
                        +
                        user.getUserFirstName()
                        + " "
                        + user.getUserLastName()
                        + ",\n\n"
                        + "We received a request to reset your password for your Expense Management App account. "
                        + "Please click the link below to reset your password: \n\n"
                        + verificationLink
                        + "\n\n"
                        + "If you did not request a password reset, please ignore this email.\n\n"
                        + "Best regards,\n"
                        + "Expense Management App Team";

        // Send email
        emailSenderService.sendEmail(emailTo, emailSubject, emailBody);

        // Redirect attributes
        redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
        redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
        redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);

        // Return view
        return "redirect:/message";
    }

    private String authenticationFormPopulation(String authenticationType, UserModel userModel, String formTitle, String formAction, Model model) {
        model.addAttribute("authenticationType", authenticationType);
        model.addAttribute("userModel", userModel);
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);

        return "Credential/Authentication/authentication-form";
    }

}
