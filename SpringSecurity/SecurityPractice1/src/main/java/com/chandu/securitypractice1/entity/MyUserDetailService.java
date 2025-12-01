package com.chandu.securitypractice1.entity;

import com.chandu.securitypractice1.repository.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    private MyUserRepo myUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<MyUser> user= myUserRepo.findByUsername(username);

    if(user.isPresent()){

        var userObj=user.get();
        return User.builder()
                .username(userObj.getUsername())
                .password(userObj.getPassword())
                .roles(getRoles(userObj))
                .build();


    }else {
        throw new UsernameNotFoundException("User not found with username: " + username);
    }



    }

    private String[] getRoles(MyUser user){
        if (user.getRole()==null){
            return new String[] {"USER"};
        }
        return user.getRole().split(",");
    }

}
