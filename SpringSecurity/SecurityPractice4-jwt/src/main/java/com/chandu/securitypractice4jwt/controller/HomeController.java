package com.chandu.securitypractice4jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @GetMapping("home")
    public String homePage(Principal principal) {
        return "<h1>Hello World!</h1> "+principal.getName();
    }
}
