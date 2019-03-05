package com.acme.architecture.common.exception;

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
