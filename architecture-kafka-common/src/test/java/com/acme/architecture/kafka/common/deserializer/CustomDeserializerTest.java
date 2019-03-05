package com.acme.architecture.kafka.common.deserializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.acme.architecture.common.deserializer.CustomDeserializer;
import com.acme.architecture.common.serializer.CustomSerializer;

public class CustomDeserializerTest {

	private final String TEST_STRING_EXAMPLE = "Test";
	private final Integer TEST_INTEGER_EXAMPLE = 1;
	
	private HashMap<Object, Object> testMap;
	
	private CustomSerializer<HashMap<Object, Object>> customSerializer;
	private CustomDeserializer<HashMap<Object, Object>> customDeserializer;
	
	@Before
	public void init() {
		testMap = new HashMap<Object, Object>();
		testMap.put("Key_1", TEST_STRING_EXAMPLE);
		testMap.put("Key_2", TEST_INTEGER_EXAMPLE);
		
		customSerializer = new CustomSerializer<HashMap<Object, Object>>();
		customDeserializer = new CustomDeserializer<HashMap<Object, Object>>();
	}

	@Test
    public void shouldDeserielize() throws Exception {
		byte[] data = customSerializer.serialize("TEST",testMap);

		HashMap<Object, Object> result = (HashMap<Object, Object>) customDeserializer.deserialize("TEST", data);
		
		assertNotNull(result);
		assertEquals(Integer.valueOf(2).intValue(),result.size());
    }
	
}