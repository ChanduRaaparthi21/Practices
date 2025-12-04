package com.chandu.securitypractice3.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {



    @GetMapping("home")
    public String homeController(Principal principal) {
        return "HomeController : for " +principal.getName();

    }

}
