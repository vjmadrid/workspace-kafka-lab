package com.acme.kafka.connector.rest.sender.exception;

import com.acme.architecture.testing.util.AbstractExceptionTestUtil;

public class KafkaRestSenderExceptionTest extends AbstractExceptionTestUtil {

	@Override
	protected Exception getExceptionWithParameter() {
		return new KafkaRestSenderException("1");
	}
	
	@Override
	protected Exception getExceptionWithThrowable() {
		return new KafkaRestSenderException(new RuntimeException());
	}

	@Override
	protected Exception getExceptionWithParameterAndThrowable() {
		return new KafkaRestSenderException("1", new RuntimeException());
	}

}
