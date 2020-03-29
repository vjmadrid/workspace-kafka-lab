package com.acme.kafka.annotation.config.constant;

public final class AnnotationKafkaConstant {

	private AnnotationKafkaConstant() {
	}

	public static final String FWK_PROP_GROUP_ID = "kafka.annotation";
	
	public static final String FWK_PROP_TOPICS_ID = "kafka.annotation.topics";
			
	public static final String SPRING_BOOT_PATTERN = "^\\$\\{[A-Za-z0-9_.]*\\}";
	
}
