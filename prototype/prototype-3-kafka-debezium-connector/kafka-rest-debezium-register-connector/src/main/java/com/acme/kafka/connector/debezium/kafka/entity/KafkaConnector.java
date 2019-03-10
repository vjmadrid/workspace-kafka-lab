package com.acme.kafka.connector.debezium.kafka.entity;

import java.util.HashMap;
import java.util.Map;

public class KafkaConnector {

	private String name;

	private Map<String,String> config = new HashMap<String,String>();

	public KafkaConnector(String name, Map<String, String> config) {
		super();
		this.name = name;
		this.config = config;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getConfig() {
		return config;
	}

	public void setConfig(Map<String, String> config) {
		this.config = config;
	}
	
}
