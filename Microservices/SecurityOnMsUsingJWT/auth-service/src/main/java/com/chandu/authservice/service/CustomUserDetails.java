package com.chandu.authservice.service;

import com.chandu.authservice.entity.UserCredential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;

    public CustomUserDetails(UserCredential userCredential) {
        this.username = userCredential.getUsername();
        this.password = userCredential.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
