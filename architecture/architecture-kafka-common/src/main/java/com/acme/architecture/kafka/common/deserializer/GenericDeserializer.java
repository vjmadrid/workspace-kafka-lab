package com.acme.architecture.kafka.common.deserializer;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Deserializer;

public class GenericDeserializer <T extends Serializable> implements Deserializer<T> {
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public T deserialize(String topic, byte[] data) {
		return SerializationUtils.deserialize(data);
	}

	@Override
	public void close() {
	}

}