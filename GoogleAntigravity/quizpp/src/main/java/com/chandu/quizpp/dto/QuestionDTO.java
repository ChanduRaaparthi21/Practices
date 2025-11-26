package com.chandu.quizpp.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
}
