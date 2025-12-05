package com.chandu.securitypractice4jwt.service;

import com.chandu.securitypractice4jwt.model.Users;
import com.chandu.securitypractice4jwt.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public void save(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepo.save(user);
    }
}
