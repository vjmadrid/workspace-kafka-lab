package com.acme.kafka.factory;

import com.acme.kafka.entity.CustomMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class CustomMessageFactory {

	public static CustomMessage create(@JsonProperty("id") int id, @JsonProperty("message") String message) {
		final CustomMessage customMessage = new CustomMessage();
		customMessage.setId(id);
		customMessage.setMessage(message);
		return customMessage;
	}
	
}
