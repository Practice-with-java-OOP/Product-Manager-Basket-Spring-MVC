package com.codegym.controller;

import com.codegym.model.Goods;
import com.codegym.model.Product;
import com.codegym.model.User;
import com.codegym.service.goods.GoodsService;
import com.codegym.service.product.ProductService;
import com.codegym.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

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

    @GetMapping("/products")
    public String listProduct(Model model, Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        model.addAttribute("products", products);
        return "/product/list";
    }

    @GetMapping("/products/create")
    public String showFormCreate(Model model) {
        User user = userService.findUserByEmail(getPrincipal());
        model.addAttribute("user", user);
        model.addAttribute("product", new Product());
        return "/product/create";
    }

    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute("product") Product product, Model model) {
        User user = userService.findUserByEmail(getPrincipal());

        productService.save(product);
        model.addAttribute("user", user);
        model.addAttribute("product", product);
        model.addAttribute("message", "Product created successfully");
        return "/image/upload";
    }

    @GetMapping("/products/edit/{id}")
    public String showFormEdit(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);

        if (product == null) {
            return "/error.404";
        }

        model.addAttribute("product", product);
        return "/product/edit";
    }

    @PostMapping("/products/edit")
    public String editProduct(@ModelAttribute("product") Product product, Model model) {
        productService.save(product);

        model.addAttribute("product", product);
        model.addAttribute("message", "Product edited successfully");
        return "/product/edit";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);

        if (product == null) {
            return "/error.404";
        }

        productService.remove(product.getId());
        return "redirect:/products";
    }

    @GetMapping("/products/detail/{id}")
    public String detailProduct(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "/error.404";
        }
        User user = userService.findUserByEmail(getPrincipal());

        model.addAttribute("user", user);
        model.addAttribute("product", product);
        return "/product/detail";
    }

    @GetMapping("/products/upload/{id}")
    public String fileUpload(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        User user = userService.findUserByEmail(getPrincipal());

        model.addAttribute("user", user);
        model.addAttribute("product", product);
        return "/image/upload";
    }

    @GetMapping("/products/addToBasket/{id}")
    public String addToBasket(@PathVariable("id") Long id, @RequestParam("quantity") int quantity) {
        Product product = productService.findById(id);
        User user = userService.findUserByEmail(getPrincipal());

        Goods goods = new Goods();
        goods.setName(product.getName());
        goods.setPrice(product.getPrice());
        goods.setQuantity(quantity);
        goods.setUser(user);

        goodsService.save(goods);
        return "redirect:/products/detail/{id}";
    }
}
