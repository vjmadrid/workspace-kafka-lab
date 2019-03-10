package com.acme.kafka.demo.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.acme.kafka.annotation.consumer.ReveiveToKafka;
import com.acme.kafka.annotation.producer.SendToKafka;

@Component
public class KafkaTopic1Processor {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaTopic1Processor.class);
	
	@SendToKafka(topics = {"${app.topic.example1}"})
	public String send(String data) {
		LOG.info("Send data: '{}'",data);
		return data;
	}

	@ReveiveToKafka(topics = {"${app.topic.example1}"}) // @KafkaListener(topics = {"topic-1"})
	@SendToKafka(topics = {"${app.topic.example2}"}) 	
    public String process(String data) {
		LOG.info("Processing data: '{}'",data);
        return ("MODIFIED "+data.toUpperCase());
    }

}