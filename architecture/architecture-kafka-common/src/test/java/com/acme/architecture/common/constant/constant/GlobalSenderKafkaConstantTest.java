package com.acme.architecture.common.constant.constant;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.acme.architecture.common.constant.GlobalReceiverKafkaConstant;
import com.acme.architecture.event.driven.factory.JUnitTestBuilder;

public class GlobalSenderKafkaConstantTest {

	@Test
	public void checkWellFormattedClass() throws NoSuchMethodException, InvocationTargetException,
			InstantiationException, IllegalAccessException {
		JUnitTestBuilder.assertUtilityClassWellDefined(GlobalReceiverKafkaConstant.class);
	}

}
