package com.acme.architecture.kafka.common.exception;

import com.acme.architecture.kafka.common.exception.AcmeKafkaException;
import com.acme.architecture.testing.util.AbstractExceptionTestUtil;

public class AcmeKafkaExceptionTest extends AbstractExceptionTestUtil {

	@Override
	protected Exception getExceptionWithParameter() {
		return new AcmeKafkaException("1");
	}
	
	@Override
	protected Exception getExceptionWithThrowable() {
		return new AcmeKafkaException(new RuntimeException());
	}

	@Override
	protected Exception getExceptionWithParameterAndThrowable() {
		return new AcmeKafkaException("1", new RuntimeException());
	}

}
