package com.yourcompany.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductPageController {

    @GetMapping("/products")
    public String listProducts() {
        // Forward to static products.html
        return "forward:/products.html";
    }

    @GetMapping("/products/**")
    public String productDetail() {
        // For product detail page, use static file
        return "forward:/product-detail.html";
    }
}



