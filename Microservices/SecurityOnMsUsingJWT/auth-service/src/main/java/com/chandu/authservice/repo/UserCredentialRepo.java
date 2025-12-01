package com.chandu.authservice.repo;

import com.chandu.authservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepo extends JpaRepository<UserCredential, Integer> {
    Optional<UserCredential> findByUsername(String username);
}
