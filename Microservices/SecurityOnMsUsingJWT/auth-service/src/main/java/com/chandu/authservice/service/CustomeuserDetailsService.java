package com.chandu.authservice.service;

import com.chandu.authservice.entity.UserCredential;
import com.chandu.authservice.repo.UserCredentialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomeuserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Optional<UserCredential> credential= userCredentialRepo.findByUsername(username);

        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
