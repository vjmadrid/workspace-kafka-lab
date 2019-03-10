package com.acme.kafka.connector.rest.sender.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.architecture.event.driven.converter.GenericEventConverter;
import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.architecture.event.driven.enumerate.GenericEventTypeEnum;
import com.acme.architecture.event.driven.factory.GenericEventDataFactory;

@Service
public class KafkaRestSenderConnectorEventServiceImpl implements KafkaRestSenderConnectorEventService {

	public static final Logger LOG = LoggerFactory.getLogger(KafkaRestSenderConnectorEventServiceImpl.class);

	@Value("${app.topic.events}")
    private String topicEvents;
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	public String sendEvent(String message) {
		GenericEvent event = GenericEventDataFactory.create(UUID.randomUUID().toString(),"", "Send Message",GenericEventTypeEnum.CREATE.toString(), "", 0, message);
		String eventJSON = GenericEventConverter.INSTANCE.fromObjectToJSON(event, false);
		LOG.info("[KafkaRestSenderService] sending event='{}' to topic='{}'", eventJSON, topicEvents);
        kafkaTemplate.send(topicEvents, eventJSON);
		return eventJSON;
	}

}
