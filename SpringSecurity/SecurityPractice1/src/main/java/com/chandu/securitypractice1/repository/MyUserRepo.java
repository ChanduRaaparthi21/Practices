package com.chandu.securitypractice1.repository;

import com.chandu.securitypractice1.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepo extends JpaRepository<MyUser,Integer> {

    Optional<MyUser> findByUsername(String username);

}
