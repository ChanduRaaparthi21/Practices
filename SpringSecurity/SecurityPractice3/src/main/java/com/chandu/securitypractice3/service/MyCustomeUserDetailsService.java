package com.chandu.securitypractice3.service;

import com.chandu.securitypractice3.model.UserPrincipal;
import com.chandu.securitypractice3.model.Users;
import com.chandu.securitypractice3.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyCustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findByUsername(username);

        if(user==null){
            System.out.println("Username not found");
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);



    }
}
