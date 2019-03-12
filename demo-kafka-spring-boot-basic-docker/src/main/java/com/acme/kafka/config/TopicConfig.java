package com.acme.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

	@Value("${app.topic.example1}")
	private String topic1Name;

	@Bean
    public NewTopic topic1() {
        return new NewTopic(topic1Name, 3, (short) 1);
    }
	
}
