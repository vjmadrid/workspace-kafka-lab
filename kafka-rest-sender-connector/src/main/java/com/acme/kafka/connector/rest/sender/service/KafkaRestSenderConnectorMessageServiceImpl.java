package com.acme.kafka.connector.rest.sender.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaRestSenderConnectorMessageServiceImpl implements KafkaRestSenderConnectorMessageService {

	public static final Logger LOG = LoggerFactory.getLogger(KafkaRestSenderConnectorMessageServiceImpl.class);

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
