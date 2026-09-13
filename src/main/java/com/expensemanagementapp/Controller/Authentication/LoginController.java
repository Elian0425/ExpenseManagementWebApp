package com.expensemanagementapp.Controller.Authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = {"/auth/login", "/"})
    public String login() {
        return "/Credential/Authentication/login-form";
    }
}
