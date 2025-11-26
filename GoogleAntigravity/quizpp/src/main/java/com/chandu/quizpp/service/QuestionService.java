package com.chandu.quizpp.service;

import com.chandu.quizpp.dto.QuestionDTO;
import com.chandu.quizpp.entity.Question;
import com.chandu.quizpp.repository.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Question question = modelMapper.map(questionDTO, Question.class);
        Question savedQuestion = questionRepository.save(question);
        return modelMapper.map(savedQuestion, QuestionDTO.class);
    }

    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setTitle(questionDTO.getTitle());
        question.setOption1(questionDTO.getOption1());
        question.setOption2(questionDTO.getOption2());
        question.setOption3(questionDTO.getOption3());
        question.setOption4(questionDTO.getOption4());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());

        Question updatedQuestion = questionRepository.save(question);
        return modelMapper.map(updatedQuestion, QuestionDTO.class);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(question -> modelMapper.map(question, QuestionDTO.class))
                .collect(Collectors.toList());
    }

    public QuestionDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return modelMapper.map(question, QuestionDTO.class);
    }
}
