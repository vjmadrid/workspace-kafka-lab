package com.acme.kafka.annotation.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaService {

	public void sendToTopic(String topic,Object message);

	@SuppressWarnings("rawtypes")
	public void genericKafkaListener(ConsumerRecord consumerRecord) throws Exception;

}