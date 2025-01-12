package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/loginPage")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/req/signup")
    public String signup() {
        return "signup";
    }
}

