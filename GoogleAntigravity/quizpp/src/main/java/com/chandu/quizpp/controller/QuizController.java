package com.chandu.quizpp.controller;

import com.chandu.quizpp.dto.UserAnswerDTO;
import com.chandu.quizpp.entity.Quiz;
import com.chandu.quizpp.entity.UserScore;
import com.chandu.quizpp.service.QuizService;
import com.chandu.quizpp.service.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserScoreService userScoreService;

    @GetMapping("/quiz/{quizId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Quiz> getQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }

    @PostMapping("/quiz/{quizId}/submit")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserScore> submitQuiz(@PathVariable Long quizId, @RequestBody List<UserAnswerDTO> answers) {
        return ResponseEntity.ok(userScoreService.submitQuiz(quizId, answers));
    }

    @GetMapping("/user/scores")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserScore>> getUserScores() {
        return ResponseEntity.ok(userScoreService.getUserScores());
    }

    @GetMapping("/quizzes")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @PostMapping("/quiz")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Quiz> createQuiz(@RequestBody com.chandu.quizpp.dto.QuizDTO quizDTO) {
        return ResponseEntity.ok(quizService.createQuiz(quizDTO));
    }

    @Autowired
    private com.chandu.quizpp.service.QuestionService questionService;

    @GetMapping("/questions")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<com.chandu.quizpp.dto.QuestionDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }
}
