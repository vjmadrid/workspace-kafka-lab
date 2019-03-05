package com.acme.kafka.connector.rest.sender.service;

public interface KafkaRestSenderConnectorEventService {

	String sendEvent(String message);
	
}
