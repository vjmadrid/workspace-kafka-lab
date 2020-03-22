package com.acme.architecture.kafka.common.exception;

import com.acme.architecture.common.exception.AcmeException;

public class AcmeKafkaException extends AcmeException {

	private static final long serialVersionUID = 2350407990921760877L;

	public AcmeKafkaException(String message) {
		super(message);
	}

	public AcmeKafkaException(Throwable cause) {
		super(cause);
	}

	public AcmeKafkaException(String message, Throwable cause) {
		super(message, cause);
	}

}
