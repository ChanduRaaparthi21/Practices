package com.chandu.quizservice.kafka;

import com.chandu.common.events.QuizCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class QuizEventProducer {

    @Value("${quiz.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, QuizCreatedEvent> kafkaTemplate;

    public QuizEventProducer(KafkaTemplate<String, QuizCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(QuizCreatedEvent event) {

        kafkaTemplate.send(topicName, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("📤 SENT → " + event.getTitle());
                    } else {
                        System.out.println("❌ FAILED → " + ex.getMessage());
                    }
                });

        System.out.println("📤 Quiz event sent → " + event.getTitle());
    }
}
