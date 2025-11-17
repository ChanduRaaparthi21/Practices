package com.chandu.questionservice.dao;

import java.util.List;

import com.chandu.questionservice.model.Question;
import org.aspectj.weaver.tools.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {



	List<Question> findByCategory(String category);

	@Query(value = "SELECT q.id FROM question q WHERE q.category = ?1 ORDER BY RAND() LIMIT ?2", nativeQuery = true)
	List<Integer> findRandomQuestionsByCategory(String categoryName, int numQ);


	

	

	
	
}
