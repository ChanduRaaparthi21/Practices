package com.chandu.quizpp.dto;

import lombok.Data;

@Data
public class UserAnswerDTO {
    private Long questionId;
    private String chosenAnswer;
}
