package com.chandu.quizservice.controller;

import java.util.List;

import com.chandu.common.events.QuizCreatedEvent;
import com.chandu.quizservice.kafka.QuizEventProducer;
import com.chandu.quizservice.model.QuestionWrapper;
import com.chandu.quizservice.model.QuizDto;
import com.chandu.quizservice.model.Response;
import com.chandu.quizservice.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@Autowired
	private QuizEventProducer quizEventProducer;

	// ✅ FIXED createQuiz
	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {

		// Step 1: Create quiz using your normal flow
		ResponseEntity<String> quizResponse =
				quizService.createQuiz(
						quizDto.getCategoryName(),
						quizDto.getNumOfQuestions(),
						quizDto.getTitle()
				);

		// Step 2: Create Kafka event
		QuizCreatedEvent event = new QuizCreatedEvent(
				quizDto.getCategoryName(),
				quizDto.getNumOfQuestions(),
				quizDto.getTitle()
		);

		// Step 3: Publish event to Kafka
		quizEventProducer.publish(event);

		// Step 4: Send response back to client
		return ResponseEntity.ok("Quiz created & event sent successfully");
	}

	// Existing Endpoints
	@GetMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
		return quizService.getQuizQuestions(id);
	}

	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
		return quizService.calculateResult(id, responses);
	}
}
