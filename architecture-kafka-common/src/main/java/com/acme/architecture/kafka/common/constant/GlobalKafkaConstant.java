package com.acme.architecture.kafka.common.constant;

public final class GlobalKafkaConstant {

	private GlobalKafkaConstant() {
	}

	public static final String DEFAULT_KAFKA_BROKERS = "localhost:9092";
	
	public static final Integer DEFAULT_MESSAGE_COUNT = 1000;
	
	public static final String DEFAULT_CLIENT_ID = "defaultClient";
	
	public static final String TOPIC_NAME = "defaultTopic";
	
	public static final String DEFAULT_GROUP_ID_CONFIG = "defaultConsumerGroup";
	
	public static final Integer DEFAULT_MAX_NO_MESSAGE_FOUND_COUNT = 100;
	
	public static final String OFFSET_RESET_LATEST = "latest";
	
	public static final String OFFSET_RESET_EARLIER = "earliest";
	
	public static final Integer DEFAULT_MAX_POLL_RECORDS = 1;
	
	public static final int DEFAULT_PARTITION_COUNT = 50;
	

}
