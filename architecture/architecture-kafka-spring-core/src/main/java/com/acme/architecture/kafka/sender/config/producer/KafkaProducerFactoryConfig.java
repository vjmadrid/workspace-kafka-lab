package com.acme.architecture.kafka.sender.config.producer;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

public final class KafkaProducerFactoryConfig {
	
	public static final ProducerFactory<String, String> producerStringFactory(final String bootstrapServers) {
		return new DefaultKafkaProducerFactory<>(KafkaProducerPropertiesConfig.producerString(bootstrapServers));
	}

}
