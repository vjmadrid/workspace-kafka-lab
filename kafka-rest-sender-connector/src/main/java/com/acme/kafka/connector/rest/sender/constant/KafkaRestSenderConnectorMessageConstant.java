package com.acme.kafka.connector.rest.sender.constant;

public final class KafkaRestSenderConnectorMessageConstant {

	private KafkaRestSenderConnectorMessageConstant() {
	}
	
	public static final String MAPPING_MESSAGE = "/api/message";

	public static final String MESSAGE_SENDED = "message.validation.info.SENDED";
	public static final String MESSAGE_NOT_SENDED = "message.validation.error.NOT_SENDED";
	
}
