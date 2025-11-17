package com.chandu.quizservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.chandu.quizservice.model.Quiz;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {

}
