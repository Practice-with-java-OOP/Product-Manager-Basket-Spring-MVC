package com.codegym.controller;

import com.codegym.model.Role;
import com.codegym.model.User;
import com.codegym.service.role.RoleService;
import com.codegym.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("roles")
    public Iterable<Role> listRole() {
        return roleService.finfAll();
    }

    @GetMapping("/users")
    public String listUser(Model model) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/create")
    public String showFormCreate(Model model) {
        model.addAttribute("user", new User());
        return "/user/create";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        new User().validate(user, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return "/user/create";
        }

        userService.save(user);
        model.addAttribute("user", user);
        model.addAttribute("message", "User created successfully");
        return "/user/create";
    }

    @GetMapping("/users/edit/{id}")
    public String showFormEdit(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);

        if (user == null) {
            return "/error.404";
        }

        model.addAttribute("user", user);
        return "/user/edit";
    }

    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        new User().validate(user, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return "/user/edit";
        }

        userService.save(user);
        model.addAttribute("user", user);
        model.addAttribute("message", "User edited information successfully");
        return "/user/edit";
    }

    @GetMapping("/users/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);

        if (user == null) {
            return "/error.404";
        }

        userService.remove(user.getId());
        return "redirect:/";
    }

    @GetMapping("/users/detail/{id}")
    public String detailUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "/error.404";
        }

        model.addAttribute("user", user);
        return "/user/detail";
    }
}
