package com.acme.architecture.kafka.common.exception;

import com.acme.architecture.common.exception.AcmeKafkaException;
import com.acme.architecture.event.driven.entity.AbstractExceptionTest;

public class AcmeKafkaExceptionTest extends AbstractExceptionTest {

	@Override
	protected Exception getExceptionWithParameter() {
		return new AcmeKafkaException("1");
	}

}
