package com.chandu.authservice.service;

import com.chandu.authservice.entity.UserCredential;
import com.chandu.authservice.repo.UserCredentialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtilService jwtUtilService;

    public String save(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepo.save(userCredential);
        return "User registered successfully";
    }

    public String generateToken(String username){
        return jwtUtilService.generateToken(username);
    }

    public void validateToken(String token){
        jwtUtilService.validateToken(token);
    }
}
