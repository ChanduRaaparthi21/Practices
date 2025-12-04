package com.chandu.securitypractice2.controller;

import com.chandu.securitypractice2.model.Users;
import com.chandu.securitypractice2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Users save(@RequestBody Users user) {
        userService.save(user);
        return user;
    }

}
