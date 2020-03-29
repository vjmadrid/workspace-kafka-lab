package com.acme.kafka.connector.rest.sender.constant;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.acme.architecture.testing.util.JUnitTestUtil;

public class WebConfigConstantTest {

	@Test
	public void whenCheckConstantClassWellDefined() throws NoSuchMethodException, InvocationTargetException,
			InstantiationException, IllegalAccessException {
		JUnitTestUtil.checkConstantClassWellDefined(WebConfigConstant.class);
	}

}
