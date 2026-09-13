package com.expensemanagementapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class NotificationMessageController {

    @GetMapping("/message")
    public String message(@ModelAttribute("notificationMessage") String notificationMessage,
                          @ModelAttribute("notificationButtonTitle") String notificationButtonTitle,
                          @ModelAttribute("notificationButtonLink") String notificationButtonLink, Model model) {

        model.addAttribute("notificationMessage", notificationMessage);
        model.addAttribute("notificationButtonTitle", notificationButtonTitle);
        model.addAttribute("notificationButtonLink", notificationButtonLink);
        return "Notification/message";
    }
}
