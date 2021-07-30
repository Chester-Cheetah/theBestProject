package com.chestercheetah.megaproject.controller;


import com.chestercheetah.megaproject.entity.User;
import com.chestercheetah.megaproject.entity.Views;
import com.chestercheetah.megaproject.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/authorized")
    @JsonView(Views.userInfo.class)
    public User authorizedUser (Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }

}
