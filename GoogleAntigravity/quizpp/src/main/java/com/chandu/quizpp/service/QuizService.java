package com.chandu.quizpp.service;

import com.chandu.quizpp.dto.QuizDTO;
import com.chandu.quizpp.entity.Question;
import com.chandu.quizpp.entity.Quiz;
import com.chandu.quizpp.entity.User;
import com.chandu.quizpp.repository.QuestionRepository;
import com.chandu.quizpp.repository.QuizRepository;
import com.chandu.quizpp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public Quiz createQuiz(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDTO.getTitle());

        List<Question> questions = questionRepository.findAllById(quizDTO.getQuestionIds());
        quiz.setQuestions(questions);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        quiz.setCreatedBy(user);

        return quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }
}
