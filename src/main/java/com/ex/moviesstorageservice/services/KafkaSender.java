package com.ex.moviesstorageservice.services;

import com.ex.moviesstorageservice.entities.EmailDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    String kafkaTopic = "kafka_topic";

    public void send(EmailDetails message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(kafkaTopic, objectMapper.writeValueAsString(message));
        LOGGER.info("Message sent to the Kafka Topic java_in_use_topic Successfully");
    }
}