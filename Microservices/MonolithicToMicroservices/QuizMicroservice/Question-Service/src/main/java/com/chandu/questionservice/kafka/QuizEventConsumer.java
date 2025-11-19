package com.chandu.questionservice.kafka;

import com.chandu.common.events.QuizCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class QuizEventConsumer {

    @KafkaListener(
            topics = "quiz-events",
            groupId = "question-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(QuizCreatedEvent event) {
        System.out.println("📩 Quiz Created Event Received:");
        System.out.println("Category: " + event.getCategoryName());
        System.out.println("Questions: " + event.getNumOfQuestions());
        System.out.println("Title: " + event.getTitle());
    }
}
