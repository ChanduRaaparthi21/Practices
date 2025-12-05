package com.chandu.securitypractice4jwt.service;

import com.chandu.securitypractice4jwt.model.Users;
import com.chandu.securitypractice4jwt.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    public void save(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepo.save(user);
    }

    public String verify(Users user) {
        Authentication authentication=
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }else {
            return "fail";
        }
    }
}
