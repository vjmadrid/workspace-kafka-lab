package com.acme.connector.kafka.spring.rest.producer.constant;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.acme.architecture.testing.util.JUnitTestUtil;
import com.acme.connector.kafka.spring.rest.producer.constant.EventConstant;

public class EventConstantTest {

	@Test
	public void whenCheckConstantClassWellDefined() throws NoSuchMethodException, InvocationTargetException,
			InstantiationException, IllegalAccessException {
		JUnitTestUtil.checkConstantClassWellDefined(EventConstant.class);
	}

}
