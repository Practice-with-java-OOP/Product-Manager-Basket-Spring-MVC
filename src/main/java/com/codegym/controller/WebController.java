package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.User;
import com.codegym.service.product.ProductService;
import com.codegym.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    private String getPrincipal() {
        String email = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return email;
    }

    @GetMapping("/")
    public String home(Model model, Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        model.addAttribute("products", products);
        return "/view/home";
    }

    @GetMapping("/login")
    public String login() {
        return "/view/login";
    }

    @GetMapping("/user")
    public String userPage(Pageable pageable, Model model) {
        Page<Product> products = productService.findAll(pageable);

        User user = userService.findUserByEmail(getPrincipal());
        model.addAttribute("user", user);
        model.addAttribute("products", products);
        return "/view/userPage";
    }

    @GetMapping("/admin")
    public String adminPage(Pageable pageable, Model model) {
        Page<Product> products = productService.findAll(pageable);
        User user = userService.findUserByEmail(getPrincipal());

        model.addAttribute("user", user);
        model.addAttribute("products", products);
        return "/view/adminPage";
    }
}
