package com.acme.connector.kafka.spring.rest.producer.constant;

public final class MessageConstant {

	private MessageConstant() {
	}
	
	public static final String MAPPING_MESSAGE = "/api/message";

	public static final String MESSAGE_SENDED = "message.validation.info.SENDED";
	public static final String MESSAGE_NOT_SENDED = "message.validation.error.NOT_SENDED";
	
}
