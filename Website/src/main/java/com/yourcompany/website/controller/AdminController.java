package com.yourcompany.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String dashboard() {
        return "forward:/admin/dashboard.html";
    }

    @GetMapping("")
    public String dashboardNoSlash() {
        return "forward:/admin/dashboard.html";
    }

    @GetMapping("/products")
    public String products() {
        return "forward:/admin/products.html";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "forward:/admin/contacts.html";
    }
}



