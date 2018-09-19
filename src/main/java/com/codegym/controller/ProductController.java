package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String listProduct(Model model, Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        model.addAttribute("products", products);
        return "/product/list";
    }

    @GetMapping("/products/create")
    public String showFormCreate(Model model) {
        model.addAttribute("product", new Product());
        return "/product/create";
    }

    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute("product") Product product, Model model) {
        productService.save(product);

        model.addAttribute("product", product);
        model.addAttribute("message", "Product created successfully");
        return "/product/create";
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

        model.addAttribute("product", product);
        return "/product/detail";
    }
}