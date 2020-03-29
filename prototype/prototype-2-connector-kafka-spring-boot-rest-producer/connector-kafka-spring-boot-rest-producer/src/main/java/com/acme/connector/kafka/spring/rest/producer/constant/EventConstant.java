package com.acme.connector.kafka.spring.rest.producer.constant;

public final class EventConstant {

	private EventConstant() {
	}
	
	public static final String MAPPING_EVENT = "/api/event";

	public static final String EVENT_SENDED = "event.validation.info.SENDED";
	public static final String EVENT_NOT_SENDED = "event.validation.error.NOT_SENDED";
	
}
