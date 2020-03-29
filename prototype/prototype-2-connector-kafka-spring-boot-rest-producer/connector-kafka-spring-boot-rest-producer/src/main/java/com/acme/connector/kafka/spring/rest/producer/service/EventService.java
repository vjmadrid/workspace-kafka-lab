package com.acme.connector.kafka.spring.rest.producer.service;

public interface EventService {

	String sendEvent(String message);
	
}
