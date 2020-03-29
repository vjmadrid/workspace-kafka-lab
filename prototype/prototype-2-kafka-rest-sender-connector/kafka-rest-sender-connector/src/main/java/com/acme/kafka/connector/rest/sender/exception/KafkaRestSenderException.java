package com.acme.kafka.connector.rest.sender.exception;

import com.acme.architecture.common.constant.GlobalConstant;
import com.acme.architecture.common.exception.AcmeException;


public class KafkaRestSenderException extends AcmeException {

	private static final long serialVersionUID = -5333357611767101717L;

	public KafkaRestSenderException(String header) {
		this(header, GlobalConstant.EMPTY_STRING);
	}
	
	public KafkaRestSenderException(String header, String message) {
		super(new StringBuilder(header).append(message).toString());
	}

}
