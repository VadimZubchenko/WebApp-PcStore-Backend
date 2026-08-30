package com.vadimzu.webpcstore.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {
    @GetMapping("/test")
    public String hello() {
        return "Application is running!";
    }
}
