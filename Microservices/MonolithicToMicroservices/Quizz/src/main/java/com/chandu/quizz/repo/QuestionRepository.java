package com.chandu.quizz.repo;

import com.chandu.quizz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {


    List<Question> findByCategory(String category);

   @Query(value = "SELECT * FROM question WHERE category = ?1 ORDER BY RAND() LIMIT ?2", nativeQuery = true)
   List<Question> findRandomQuestionsByCategory(String category, int noOfQuestions);
}
