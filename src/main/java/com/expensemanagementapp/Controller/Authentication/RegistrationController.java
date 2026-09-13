package com.expensemanagementapp.Controller.Authentication;


import com.expensemanagementapp.Config.ApplicationProperties;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Entity.UserGender;
import com.expensemanagementapp.Model.UserModel;
import com.expensemanagementapp.Service.EmailSenderService;
import com.expensemanagementapp.Service.UserRegistrationService;
import com.expensemanagementapp.Service.UserService;
import com.expensemanagementapp.Service.UserVerificationTokenService;
import com.expensemanagementapp.Validation.Group.RegistrationValidationGroup;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/auth")
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private UserVerificationTokenService userVerificationTokenService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @GetMapping("/registration")
    public String registration(UserModel userModel, Model model) {
        List<UserGender> userGenderList = userService.getAllUserGenderAsc();
        return registrationPopulationForm(userModel, userGenderList, model);
    }

    @PostMapping("/registration")
    public String registerUser(@Validated(RegistrationValidationGroup.class) @ModelAttribute UserModel userModel,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            List<UserGender> userGenderList = userService.getAllUserGenderAsc();
            return registrationPopulationForm(userModel, userGenderList, model);
        }

        // Register the user
        User user = userRegistrationService.registerUser(userModel);


        /* EMAIL NOTIFICATION */

        // Prepare Email Notification
        String emailTo = userModel.getUserEmail();
        String emailSubject = "Welcome to Expense Management App";

        String token = userVerificationTokenService.generateToken(user);
        String verificationLink = applicationProperties.getBaseUrl() + "/auth/activation?token=" + token;

        String emailBody =
                "Dear, "
                        + userModel.getUserFirstName()
                        + " "
                        + userModel.getUserLastName()
                        + ",\n\n"
                        + "Thank you for registering at Expense Management App. We are excited to have you on board!\n\n" +
                        "Please, click on the link to activate your account: "
                        + verificationLink
                        + "\n\n"
                        + "If you have any questions or need assistance, feel free to reach out to our support team \n" + "http://localhost:8084/support" + ".\n\n\n" +
                        "Best regards,"
                        + "\n\n"
                        + "Expense Management App Team";

        // Send email
        emailSenderService.sendEmail(emailTo, emailSubject, emailBody);

        /* IN APP NOTIFICATION */
        // Prepare notification attributes
        String notificationMessage = "Registration Successful. Please check your email to activate your account.";
        String notificationButtonTitle = "Go to Login";
        String notificationButtonLink = "/auth/login";


        // Send notification attributes via RedirectAttributes
        redirectAttributes.addFlashAttribute("notificationMessage", notificationMessage);
        redirectAttributes.addFlashAttribute("notificationButtonTitle", notificationButtonTitle);
        redirectAttributes.addFlashAttribute("notificationButtonLink", notificationButtonLink);


        // Redirect to the message page
        return "redirect:/message";
    }

    private String registrationPopulationForm(UserModel userModel, List<UserGender> userGenderList, Model model) {
        model.addAttribute("userGenderList", userGenderList);
        model.addAttribute("userModel", userModel);
        return "/Credential/Authentication/registration-form";
    }
}
