package com.chandu.securitypractice1.controller;

import com.chandu.securitypractice1.entity.MyUser;
import com.chandu.securitypractice1.repository.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistraionController {

    @Autowired
    private MyUserRepo myUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("register/user")
    public MyUser registerUser(@RequestBody MyUser myUser){
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        myUserRepo.save(myUser);
        return myUser;
    }
}
