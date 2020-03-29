package com.acme.connector.kafka.spring.rest.producer.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.architecture.common.util.converter.JsonConverter;
import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.architecture.event.driven.enumerate.GenericEventTypeEnum;
import com.acme.architecture.event.driven.factory.GenericEventDataFactory;
import com.acme.connector.kafka.spring.rest.producer.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class EventServiceImpl implements EventService {

	public static final Logger LOG = LoggerFactory.getLogger(EventServiceImpl.class);

	@Value("${app.topic.events}")
    private String topicEvents;
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	public String sendEvent(String message) {
		GenericEvent event = GenericEventDataFactory.create(UUID.randomUUID().toString(),"", "Send Message",GenericEventTypeEnum.CREATE.toString(), "", 0, message);
		String eventJSON = null;
		try {
			eventJSON = JsonConverter.convertObjectToJson(event, false);
			LOG.info("[KafkaRestSenderService] sending event='{}' to topic='{}'", eventJSON, topicEvents);
			
	        kafkaTemplate.send(topicEvents, eventJSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return eventJSON;
	}

}
