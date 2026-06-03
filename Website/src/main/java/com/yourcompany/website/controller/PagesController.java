package com.yourcompany.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/projects")
    public String projects() {
        return "forward:/projects.html";
    }

    @GetMapping("/news")
    public String news() {
        return "forward:/news.html";
    }
}

