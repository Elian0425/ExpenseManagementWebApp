package com.expensemanagementapp.Controller.Dashboard;

import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/user")
public class UserDashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/menu")
    public String menu() {
        return "Dashboard/user-menu-dashboard";
    }

    @GetMapping("/profile")
    public String userProfileDashboard(Model model, Authentication authentication) {

        String userEmail = authentication.getName();
        User user = userService.getUserByEmail(userEmail);
        model.addAttribute("user", user);

        return "Dashboard/user-profile-dashboard";
    }
}
