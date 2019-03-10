package com.acme.architecture.common.constant.constant;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.acme.architecture.common.constant.GlobalKafkaConstant;
import com.acme.architecture.event.driven.factory.JUnitTestBuilder;

public class GlobalKafkaConstantTest {

	@Test
	public void checkWellFormattedClass() throws NoSuchMethodException, InvocationTargetException,
			InstantiationException, IllegalAccessException {
		JUnitTestBuilder.assertUtilityClassWellDefined(GlobalKafkaConstant.class);
	}

}
