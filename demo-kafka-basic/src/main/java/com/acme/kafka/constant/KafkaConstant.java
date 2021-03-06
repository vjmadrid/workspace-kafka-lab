package com.acme.kafka.constant;

public final class KafkaConstant {

	private KafkaConstant() {
	}

	public static final String DEFAULT_KAFKA_SERVER_URL = "localhost";
	
	public static final int DEFAULT_KAFKA_SERVER_PORT = 9092;
	
	public static final String DEFAULT_KAFKA_BROKERS = DEFAULT_KAFKA_SERVER_URL+":"+DEFAULT_KAFKA_SERVER_PORT;
	
	public static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
	
	public static final Integer DEFAULT_MESSAGE_COUNT = 1000;
	
	public static final String DEFAULT_CLIENT_ID = "defaultClient";
	
	public static final String TOPIC_NAME = "defaultTopic";

}
