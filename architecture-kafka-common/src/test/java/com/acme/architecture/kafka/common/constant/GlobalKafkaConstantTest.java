package com.acme.architecture.kafka.common.constant;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.acme.architecture.common.constant.GlobalConstant;

import es.dinersclub.architecture.testing.util.JUnitTestBuilder;

public class GlobalKafkaConstantTest {

	@Test
	public void checkWellFormattedClass() throws NoSuchMethodException, InvocationTargetException,
			InstantiationException, IllegalAccessException {
		JUnitTestBuilder.assertUtilityClassWellDefined(GlobalConstant.class);
	}

}
