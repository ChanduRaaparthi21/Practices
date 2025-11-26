package com.chandu.quizpp.service;

import com.chandu.quizpp.dto.UserAnswerDTO;
import com.chandu.quizpp.entity.Question;
import com.chandu.quizpp.entity.Quiz;
import com.chandu.quizpp.entity.User;
import com.chandu.quizpp.entity.UserScore;
import com.chandu.quizpp.repository.QuizRepository;
import com.chandu.quizpp.repository.UserRepository;
import com.chandu.quizpp.repository.UserScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserScoreService {

    @Autowired
    private UserScoreRepository userScoreRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    public UserScore submitQuiz(Long quizId, List<UserAnswerDTO> answers) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        Map<Long, String> questionAnswers = quiz.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, Question::getCorrectAnswer));

        int score = 0;
        for (UserAnswerDTO answer : answers) {
            if (questionAnswers.containsKey(answer.getQuestionId())) {
                String correctAnswer = questionAnswers.get(answer.getQuestionId());
                if (correctAnswer.equalsIgnoreCase(answer.getChosenAnswer())) {
                    score++;
                }
            }
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserScore userScore = new UserScore();
        userScore.setUser(user);
        userScore.setQuiz(quiz);
        userScore.setScore(score);

        return userScoreRepository.save(userScore);
    }

    public List<UserScore> getUserScores() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userScoreRepository.findByUserId(user.getId());
    }
}
