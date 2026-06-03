package com.yourcompany.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index() {
        // Forward to the static index.html under /static so Spring doesn't try to resolve a view name
        return "forward:/index.html";
    }
}
