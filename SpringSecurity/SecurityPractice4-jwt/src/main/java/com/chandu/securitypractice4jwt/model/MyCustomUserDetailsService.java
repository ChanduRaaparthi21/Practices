package com.chandu.securitypractice4jwt.model;

import com.chandu.securitypractice4jwt.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepo.findByUsername(username);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException(username);

        }

        return new UserPrincipal(user);
    }
}
