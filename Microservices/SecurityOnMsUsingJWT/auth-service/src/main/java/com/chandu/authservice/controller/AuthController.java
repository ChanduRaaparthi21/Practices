package com.chandu.authservice.controller;

import com.chandu.authservice.dto.AuthRequest;
import com.chandu.authservice.entity.UserCredential;
import com.chandu.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public String registerUser(@RequestBody UserCredential userCredential) {
        return authService.save(userCredential);
    }

    @PostMapping("/token")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if(authenticate.isAuthenticated()){
            return authService.generateToken(authRequest.getUsername());
        }
        else {
            throw new RuntimeException("Invalid access");

        }

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";

    }
}
