package com.ecomerce.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.version}")
public class TestController {
    @GetMapping("/ping")
    public String ping() {
        System.out.println(">>> PING called");
        return "pong";
    }
}
