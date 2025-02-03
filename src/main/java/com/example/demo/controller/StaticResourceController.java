package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticResourceController {

    @GetMapping("/contact")
    public String getContactPage() {
        return "contact"; 
    }
}

