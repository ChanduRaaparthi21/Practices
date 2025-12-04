package com.chandu.securitypractice3.controller;

import com.chandu.securitypractice3.model.Users;
import com.chandu.securitypractice3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Users saveUser(@RequestBody Users user) {
        userService.save(user);
        return user;
    }

}
