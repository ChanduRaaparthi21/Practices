package com.chandu.quizpp.repository;

import com.chandu.quizpp.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    List<UserScore> findByUserId(Long userId);
}
