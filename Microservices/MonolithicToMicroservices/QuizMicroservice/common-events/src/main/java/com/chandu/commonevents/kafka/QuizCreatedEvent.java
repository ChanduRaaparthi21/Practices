package com.chandu.quizservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class QuizCreatedEvent {

    private String categoryName;
    private int numOfQuestions;
    private String title;

}
