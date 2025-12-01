package com.chandu.securitypractice1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/home")
    public String home(){
        return "Welcome to User Home Page";
    }


    @GetMapping("/admin/home")
    public String adminHome(){
        return "Welcome to Admin Home Page";
    }

    @GetMapping("/user/home")
    public String  userHome(){
        return "Welcome to User Home Page";
    }



}
