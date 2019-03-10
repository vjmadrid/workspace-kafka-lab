package com.acme.kafka.connector.debezium.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.acme.kafka.connector.debezium.kafka.entity.KafkaConnector;

@Configuration
public class CustomFileKafkaConnectorConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomFileKafkaConnectorConfig.class);

	@Value("${kafka.connector.name}")
	private String name;
	
	@Value("${kafka.connector.config-file}")
	private String configFile;

	private Map<String,String> configMap = new HashMap<String,String>();

	@PostConstruct
	public void init() throws IOException {
		LOG.info("[CustomFileKafkaConnectorConfig] file : {}",configFile);
		Properties props = PropertiesLoaderUtils.loadAllProperties(configFile);
		props.keySet().forEach(key -> {
			this.configMap.put((String) key,props.getProperty((String) key));
		});
		LOG.info("[CustomFileKafkaConnectorConfig] File Properties Loaded...");
	}

	@Bean
	public KafkaConnector customFileKafkaConnector() {
		return new KafkaConnector(name,configMap);
	}

}