package com.acme.architecture.kafka.sender.config.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.acme.architecture.common.partitioner.CustomPartitioner;

public final class KafkaProducerPropertiesConfig {

	public static final Map<String, Object> producerString(final String bootstrapServers) {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		// props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);
		return props;
	}
	
	public static final Map<String, Object> producerStringWithPartitioner(final String bootstrapServers) {
		Map<String, Object> props = producerString(bootstrapServers);
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);
		return props;
	}
	
	public static final Map<String, Object> producerJSON(final String bootstrapServers) {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}

	public static final Properties propertiesByteArray(final String bootstrapServers) {
		Properties producerConfig = new Properties();
		producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.ByteArraySerializer");
		return producerConfig;
	}

}
