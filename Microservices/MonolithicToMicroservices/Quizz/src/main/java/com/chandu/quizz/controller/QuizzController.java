package com.chandu.quizz.controller;

import com.chandu.quizz.model.Question;
import com.chandu.quizz.model.QuestionWrapper;
import com.chandu.quizz.model.Quizz;
import com.chandu.quizz.model.Response;
import com.chandu.quizz.service.QuizzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizz")
public class QuizzController {

    @Autowired
    private QuizzService quizzService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuizz(@RequestParam String category, @RequestParam int noOfQuestions, @RequestParam String title) {
        return quizzService.createQuizz(category, noOfQuestions, title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizzQuestions(@PathVariable Integer id) {
        return quizzService.getQuizzQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuizz(@PathVariable Integer id, @RequestBody List<Response> responces){
return quizzService.calcuateScore(id, responces);
    }

}
