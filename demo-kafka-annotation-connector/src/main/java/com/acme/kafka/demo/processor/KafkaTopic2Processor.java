package com.acme.kafka.demo.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.acme.kafka.annotation.consumer.ReveiveToKafka;

@Component
public class KafkaTopic2Processor {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaTopic2Processor.class);
	
	@ReveiveToKafka(topics = {"${app.topic.example2}"}) // @KafkaListener(topics = {"topic-2"})
    public void result(String data) {
		LOG.info("Consuming data: '{}'",data);
    }
	
}