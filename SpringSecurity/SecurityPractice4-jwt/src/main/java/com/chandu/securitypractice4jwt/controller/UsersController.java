package com.chandu.securitypractice4jwt.controller;

import com.chandu.securitypractice4jwt.model.Users;
import com.chandu.securitypractice4jwt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public Users saveUser(@RequestBody Users user) {

        usersService.save(user);
        return user;
    }

}
