package com.chandu.quizservice.kafka;

import com.chandu.common.events.QuizCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class QuizEventProducer {

    private static final Logger log = LoggerFactory.getLogger(QuizEventProducer.class);

    @Value("${quiz.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, QuizCreatedEvent> kafkaTemplate;

    public QuizEventProducer(KafkaTemplate<String, QuizCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(QuizCreatedEvent event) {

        log.info("Publishing Quiz event → {}", event);

        Message<QuizCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topicName)   // FIXED
                .build();

        kafkaTemplate.send(message)   // SEND ONLY ONCE
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("📤 SENT → {}", event.getTitle());
                    } else {
                        log.error("❌ FAILED → {}", ex.getMessage());
                    }
                });
    }
}
