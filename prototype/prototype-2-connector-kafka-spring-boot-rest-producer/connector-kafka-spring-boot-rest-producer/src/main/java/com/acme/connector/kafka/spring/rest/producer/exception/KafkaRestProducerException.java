package com.acme.connector.kafka.spring.rest.producer.exception;

import com.acme.architecture.common.exception.AcmeException;



public class KafkaRestProducerException extends AcmeException {

	private static final long serialVersionUID = 7617195723824005019L;

	public KafkaRestProducerException(String message) {
		super(message);
	}

	public KafkaRestProducerException(Throwable cause) {
		super(cause);
	}

	public KafkaRestProducerException(String message, Throwable cause) {
		super(message, cause);
	}

}
