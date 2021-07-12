package com.chestercheetah.megaproject.controller.admin;

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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String printWelcomeAdmin(ModelMap model, Principal principal) {
        List<String> messages = new ArrayList<>();
        messages.add("Привет, " + principal.getName() + ":");
        messages.add("Ты оказался на главной странице портала невидимых юзеров");
        messages.add("Но ты можешь о них узнать");
        messages.add("Мы позаботились и сохранили для тебя реестр забытых юзеров");
        messages.add("Ты готов?");
        model.addAttribute("messages", messages);
        return "admin/admin";
    }

    @GetMapping("/users")
    public String userListView (Model model) {
        model.addAttribute("userListForYou", userService.getUserList());

        try {
            roleService.saveRoleListIfNotSaved();
        } catch (Exception ignored) {}

        return "admin/users";
    }

    @GetMapping("/users/id{id}")
    public String printUserProfile(ModelMap model, Principal principal, @PathVariable int id) {
        List<String> messages = new ArrayList<>();
        messages.add("Данные о пользователе " + userService.getUserByID(id).getUsername() + ":");
        model.addAttribute("messages", messages);
        model.addAttribute("profile", userService.getUserByID(id));
        return "admin/userProfile";
    }


    @GetMapping("/new")
    public String newUser (Model model) {
        model.addAttribute("userElement", new User());
        model.addAttribute("rolesForUser", roleService.roleList());
        return "admin/new";
    }

    @PostMapping("/signup")
    public String create(@ModelAttribute ("newUser") User user, @RequestParam(name = "selectedRoles") String [] selectedRoles){

        user.setRoles(getUserRoles(selectedRoles));
        boolean added = userService.save(user);
        if (!added){
            return "admin/errorMessage";
        }
        return "redirect:/admin/users";
    }

    private Set<Role> getUserRoles (String [] selectedRoles) {
        Set<Role> rolesForNewUser = new LinkedHashSet<>();
        for (String role : selectedRoles) {
            rolesForNewUser.add(roleService.getRole(role));
        }
        return rolesForNewUser;
    }

    @GetMapping("/edit/id{id}")
    public String edit (Model model, @PathVariable("id") int id) {
        model.addAttribute("userEdition", userService.getUserByID(id));
        model.addAttribute("rolesForUser", roleService.roleList());
        return "admin/edit";
    }

    @PostMapping("/save/id{id}")
    public String update (@ModelAttribute("userEdition") User user, @PathVariable("id") int id,
                          @RequestParam(name = "updatedRoles") String [] selectedRoles) {
        user.setRoles(getUserRoles(selectedRoles));
        if (!userService.update(user)) {
            return "admin/errorMessage";
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/id{id}")
    public String delete (@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
