package com.chandu.multithreading01.repository;

import com.chandu.multithreading01.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
