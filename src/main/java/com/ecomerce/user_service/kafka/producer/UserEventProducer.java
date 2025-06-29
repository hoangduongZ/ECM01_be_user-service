package com.ecomerce.user_service.kafka.producer;

import com.ecomerce.user_service.kafka.event.GenericEventWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topics.user-created}")
    private String userCreatedTopic;
    @Value("${kafka.topics.user-get-detail}")
    private String userGetDetailTopic;

    public UserEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUserCreatedEvent(GenericEventWrapper<?> event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(userCreatedTopic, event.getEventId(), payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }

    public void sendUserGetDetail(GenericEventWrapper<?> event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(userGetDetailTopic, event.getEventId(), payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }
}
