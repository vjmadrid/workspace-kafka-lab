package com.acme.kafka.connector.rest.sender.constant;

public final class KafkaRestSenderConnectorEventConstant {

	private KafkaRestSenderConnectorEventConstant() {
	}
	
	public static final String MAPPING_EVENT = "/api/event";

	public static final String EVENT_SENDED = "event.validation.info.SENDED";
	public static final String EVENT_NOT_SENDED = "event.validation.error.NOT_SENDED";
	
}
