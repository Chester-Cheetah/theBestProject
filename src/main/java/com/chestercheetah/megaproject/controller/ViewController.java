package com.chestercheetah.megaproject.controller;


import com.chestercheetah.megaproject.entity.User;
import com.chestercheetah.megaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class ViewController {

    private final UserService userService;

    @Autowired
    public ViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping ("/user")
    public String printUserPage () {
        return "user";
    }

    @GetMapping("/admin")
    public String userListView () {
        return "admin";
    }

    @GetMapping ("/login")
    public String loginPage () {
        return "login";
    }

    @GetMapping
    public String redirectUserPage(Principal principal) {
        User authorized = userService.getUserByEmail(principal.getName());
        return authorized.getRolesString().contains("ADMIN") ? "redirect:/admin" : "redirect:/user";
    }
}
