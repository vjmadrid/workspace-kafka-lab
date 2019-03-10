package com.acme.kafka.connector.debezium.kafka.converter;

import com.acme.kafka.connector.debezium.kafka.entity.KafkaConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum KafkaConnectorConverter {

	INSTANCE;

	public String fromKafkaConnectorToJSON(KafkaConnector kafkaConnector, boolean activePretty) {
		try {
			if (activePretty) {
				return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(kafkaConnector);
			} else {
				return new ObjectMapper().writeValueAsString(kafkaConnector);
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
