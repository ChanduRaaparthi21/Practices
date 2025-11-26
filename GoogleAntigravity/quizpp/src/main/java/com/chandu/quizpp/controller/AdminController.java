package com.chandu.quizpp.controller;

import com.chandu.quizpp.dto.QuestionDTO;
import com.chandu.quizpp.dto.QuizDTO;
import com.chandu.quizpp.entity.Quiz;
import com.chandu.quizpp.service.QuestionService;
import com.chandu.quizpp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @PostMapping("/question")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.createQuestion(questionDTO));
    }

    @PutMapping("/question/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.updateQuestion(id, questionDTO));
    }

    @DeleteMapping("/question/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/quiz")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDTO quizDTO) {
        return ResponseEntity.ok(quizService.createQuiz(quizDTO));
    }

    @GetMapping("/quiz")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }
}
