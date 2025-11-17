package com.chandu.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // inject actual server.port so response shows which instance served the request
    @Value("${server.port}")
    private String port;

    @GetMapping("/users")
    public String getUsers() {
        return "User Service: response from port " + port;
    }
}
