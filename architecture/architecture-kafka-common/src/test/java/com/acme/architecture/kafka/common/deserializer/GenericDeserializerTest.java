package com.acme.architecture.kafka.common.deserializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.acme.architecture.kafka.common.deserializer.GenericDeserializer;
import com.acme.architecture.kafka.common.serializer.GenericSerializer;

public class GenericDeserializerTest {

	private final String TEST_STRING_EXAMPLE = "Test";
	private final Integer TEST_INTEGER_EXAMPLE = 1;
	
	private HashMap<Object, Object> testMap;
	
	private GenericSerializer<HashMap<Object, Object>> genericSerializer;
	private GenericDeserializer<HashMap<Object, Object>> genericDeserializer;
	
	@Before
	public void init() {
		testMap = new HashMap<Object, Object>();
		testMap.put("Key_1", TEST_STRING_EXAMPLE);
		testMap.put("Key_2", TEST_INTEGER_EXAMPLE);
		
		genericSerializer = new GenericSerializer<HashMap<Object, Object>>();
		genericDeserializer = new GenericDeserializer<HashMap<Object, Object>>();
	}

	@Test
    public void shouldDeserielize() throws Exception {
		byte[] data = genericSerializer.serialize("TEST",testMap);

		HashMap<Object, Object> result = (HashMap<Object, Object>) genericDeserializer.deserialize("TEST", data);
		
		assertNotNull(result);
		assertEquals(Integer.valueOf(2).intValue(),result.size());
    }
	
}