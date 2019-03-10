package com.acme.architecture.common.constant;

public final class GlobalReceiverKafkaConstant {

	private GlobalReceiverKafkaConstant() {
	}

	public static final String DEFAULT_GROUP_ID_CONFIG = "example-group";
	
	public static final String DEFAULT_CLIENT_ID = "example-consumer";
	
	public static final Integer DEFAULT_MAX_NO_MESSAGE_FOUND_COUNT = 100;
	
	public static final String OFFSET_RESET_LATEST = "latest";
	
	public static final String OFFSET_RESET_EARLIER = "earliest";
	
	public static final Integer DEFAULT_MAX_POLL_RECORDS = 1;
	
	public static final int DEFAULT_PARTITION_COUNT = 50;
	
	public static final int COUNTDOWNLATCH = 1;
	

}
