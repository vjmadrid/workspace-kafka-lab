package com.acme.connector.kafka.spring.rest.producer.exception;

import com.acme.architecture.testing.util.AbstractExceptionTestUtil;
import com.acme.connector.kafka.spring.rest.producer.exception.KafkaRestProducerException;

public class KafkaRestProducerExceptionTest extends AbstractExceptionTestUtil {

	@Override
	protected Exception getExceptionWithParameter() {
		return new KafkaRestProducerException("1");
	}
	
	@Override
	protected Exception getExceptionWithThrowable() {
		return new KafkaRestProducerException(new RuntimeException());
	}

	@Override
	protected Exception getExceptionWithParameterAndThrowable() {
		return new KafkaRestProducerException("1", new RuntimeException());
	}

}
