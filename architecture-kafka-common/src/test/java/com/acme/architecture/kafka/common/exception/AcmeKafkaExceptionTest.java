package com.acme.architecture.kafka.common.exception;

import com.acme.architecture.common.exception.AcmeKafkaException;

import es.dinersclub.architecture.testing.exception.test.AbstractExceptionTest;

public class AcmeKafkaExceptionTest extends AbstractExceptionTest {

	@Override
	protected Exception getExceptionWithParameter() {
		return new AcmeKafkaException("1");
	}

}
