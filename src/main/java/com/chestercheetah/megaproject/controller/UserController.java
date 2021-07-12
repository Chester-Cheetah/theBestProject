package com.chestercheetah.megaproject.controller;

import com.chestercheetah.megaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping ("/user")
	public String printWelcomeUser (ModelMap model, Principal principal) {
		List<String> messages = new ArrayList<>();
		messages.add("Привет, " + principal.getName() + ":");
		model.addAttribute("messages", messages);
		model.addAttribute("profile", userService.getUserByName(principal.getName()));
		return "user";
	}

	@GetMapping
	public String redirectHomePage() {
		return "redirect:/user";
	}
}