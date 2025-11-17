package com.chandu.quizz.service;

import com.chandu.quizz.model.Question;
import com.chandu.quizz.model.QuestionWrapper;
import com.chandu.quizz.model.Quizz;
import com.chandu.quizz.model.Response;
import com.chandu.quizz.repo.QuestionRepository;
import com.chandu.quizz.repo.QuizzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizzService {

    @Autowired
    private QuizzRepository quizzRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<String> createQuizz(String category, int noOfQuestions, String title) {

        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, noOfQuestions);

        Quizz quizz = new Quizz();
        quizz.setTitle(title);
        quizz.setQuestions(questions);
        quizzRepository.save(quizz);

        return  new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizzQuestions(Integer id) {

        Optional<Quizz> quizz = quizzRepository.findById(id);

            List<Question> questionsFromDb = quizz.get().getQuestions();
            List<QuestionWrapper> questionForuser = new ArrayList<>();

            for (Question question : questionsFromDb) {
             QuestionWrapper qw = new QuestionWrapper(question.getId(), question.getQuestionTitle(),
                     question.getOption1(),
                     question.getOption2(),
                     question.getOption3(),
                     question.getOption4());
                questionForuser.add(qw);

            }
            return new ResponseEntity<>(questionForuser, HttpStatus.OK);
            // Process the questions as needed



    }

    public ResponseEntity<Integer> calcuateScore(Integer id, List<Response> responces) {

        Optional<Quizz> quizz = quizzRepository.findById(id);

        List<Question> questionsFromDb = quizz.get().getQuestions();

        int score = 0;
        int i=0;

        for (Response response : responces) {
            if(response.getResponse().equals(questionsFromDb.get(i).getRightAnswer())){
                score++;

            }
            i++;
        }

        return new ResponseEntity<>(score, HttpStatus.OK);

    }
}


