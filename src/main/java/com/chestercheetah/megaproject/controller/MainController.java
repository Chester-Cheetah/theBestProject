package com.chestercheetah.megaproject.controller;

import com.chestercheetah.megaproject.entity.Role;
import com.chestercheetah.megaproject.entity.User;
import com.chestercheetah.megaproject.service.RoleService;
import com.chestercheetah.megaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;


@Controller
public class MainController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public MainController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping ("/login")
    public String loginPage () {
        return "login";
    }

    @GetMapping ("/user")
    public String printUserPage (ModelMap model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("headerMessage", getHeaderMessage(user));
        return "user";
    }

    @GetMapping("/admin")
    public String userListView (Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("userListForYou", userService.getUserList());
        model.addAttribute("userElement", new User());
        model.addAttribute("headerMessage", getHeaderMessage(user));
        return "admin";
    }

    @GetMapping
    public String redirectHomePage() {
        return "redirect:/user";
    }

    @PostMapping("/admin/signup")
    public String create(@ModelAttribute ("newUser") User user, @RequestParam(name = "updatedRoles", required = false) String [] selectedRoles){
        user.setRoles(getUserRoles(selectedRoles));
        userService.save(user);
        return "redirect:/admin";
    }

    private Set<Role> getUserRoles (String [] selectedRoles) {
        Set<Role> rolesForNewUser = new LinkedHashSet<>();
        for (String role : selectedRoles) {
            rolesForNewUser.add(roleService.getRole(role));
        }
        if (rolesForNewUser.isEmpty()){
            rolesForNewUser.add(roleService.getRole("ROLE_USER"));
        }
        return rolesForNewUser;
    }

    @PostMapping("/admin/save/id{id}")
    public String update (@ModelAttribute("userEdition") User user, @PathVariable("id") int id,
                          @RequestParam(required = false, name = "updatedRoles") String [] selectedRoles){
        user.setRoles(getUserRoles(selectedRoles));
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/id{id}")
    public String delete (@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    // Это метод по получению строки для отображения в шапке вэб-страницы
    private String getHeaderMessage (User user) {
        return user.getEmail() + " with roles: " + user.getRolesString();
    }
}
