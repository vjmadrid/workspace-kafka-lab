package com.acme.connector.kafka.spring.rest.producer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.connector.kafka.spring.rest.producer.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	public static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Value("${app.topic.messages}")
    private String topicMessages;
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	public String send(String message) {
		LOG.info("[KafkaRestSenderService] sending message='{}' to topic='{}'", message, topicMessages);
        kafkaTemplate.send(topicMessages, message);
		return message;
	}

}
