package com.acme.kafka.connector.rest.sender.constant;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.acme.architecture.event.driven.factory.JUnitTestBuilder;

public class WebConfigConstantTest {

	@Test
	public void checkWellFormattedClass() throws NoSuchMethodException, InvocationTargetException,
			InstantiationException, IllegalAccessException {
		JUnitTestBuilder.assertUtilityClassWellDefined(WebConfigConstant.class);
	}

}
