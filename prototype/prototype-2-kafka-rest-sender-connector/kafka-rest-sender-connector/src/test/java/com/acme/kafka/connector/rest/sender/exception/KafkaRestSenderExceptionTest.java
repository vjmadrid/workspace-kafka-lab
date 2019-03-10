package com.acme.kafka.connector.rest.sender.exception;

import com.acme.architecture.event.driven.entity.AbstractExceptionTest;
import com.acme.kafka.connector.rest.sender.exception.KafkaRestSenderException;

public class KafkaRestSenderExceptionTest extends AbstractExceptionTest {

	@Override
	protected Exception getExceptionWithParameter() {
		return new KafkaRestSenderException("1");
	}

}
