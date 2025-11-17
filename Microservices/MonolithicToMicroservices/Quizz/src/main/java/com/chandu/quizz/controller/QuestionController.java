package com.chandu.quizz.controller;

import com.chandu.quizz.model.Question;
import com.chandu.quizz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("getAllQuestions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions().getBody();
    }


    @GetMapping("category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category) {
        return questionService.findByCategory(category);
    }


    @PostMapping("addQuestion")
    public ResponseEntity<Question> AddQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }


    @PutMapping("updateQuestion/{id}")
    public Question updateQuestion(@RequestBody Question question, @PathVariable Integer id) {
        return questionService.updateQuestion(question, id);
    }

    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        String message = questionService.deleteQuestion(id);
        return ResponseEntity.ok(message);
    }
}