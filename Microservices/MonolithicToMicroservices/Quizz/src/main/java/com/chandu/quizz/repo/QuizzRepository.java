package com.chandu.quizz.repo;

import com.chandu.quizz.model.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizzRepository extends JpaRepository<Quizz, Integer> {
}
