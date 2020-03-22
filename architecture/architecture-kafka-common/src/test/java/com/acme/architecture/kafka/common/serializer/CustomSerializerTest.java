package com.acme.architecture.kafka.common.serializer;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.acme.architecture.kafka.common.serializer.CustomSerializer;

public class CustomSerializerTest {

	private final String TEST_STRING_EXAMPLE = "Test";
	private final Integer TEST_INTEGER_EXAMPLE = 1;
	
	private HashMap<Object, Object> testMap;
	
	private CustomSerializer<HashMap<Object, Object>> customSerializer;

	@Before
	public void init() {
		testMap = new HashMap<Object, Object>();
		testMap.put("Key_1", TEST_STRING_EXAMPLE);
		testMap.put("Key_2", TEST_INTEGER_EXAMPLE);
		
		customSerializer = new CustomSerializer<HashMap<Object, Object>>();
	}

	@Test
    public void shouldDeSerialize() throws Exception {
		byte[] data = customSerializer.serialize("TEST",testMap);
	
		assertNotNull(data);
    }
	
}