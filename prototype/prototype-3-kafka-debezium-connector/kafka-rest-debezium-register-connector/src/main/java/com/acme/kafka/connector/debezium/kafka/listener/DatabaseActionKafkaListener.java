package com.acme.kafka.connector.debezium.kafka.listener;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.acme.kafka.connector.debezium.database.entity.DBActionData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DatabaseActionKafkaListener {

	private static final Logger LOG = LoggerFactory.getLogger(DatabaseActionKafkaListener.class);

	@PostConstruct
	public void init() throws Exception {
		LOG.info("[DatabaseActionKafkaListener] started ...");
	}
	
	private void showAction(ConsumerRecord<String, String> consumerRecord) throws JsonParseException, JsonMappingException, IOException {
		if (consumerRecord != null){
			// Parse JSON
			ObjectMapper mapper = new ObjectMapper();
			DBActionData dbActionData = mapper.readValue(consumerRecord.value(), DBActionData.class);

			LOG.info("Received data: {}",dbActionData);
		} else {
			LOG.error("No consumer record");
		}
	}
	
	@KafkaListener(topics = "${kafka.connector.listener.topic}")
	public void databaseActionKafkaListener(ConsumerRecord<String, String> consumerRecord) throws JsonParseException, JsonMappingException, IOException {
		LOG.info("[DatabaseActionKafkaListener] Received message: [topic: '{}'; partition: '{}'; offset: '{}'; key: '{}';]",consumerRecord.topic(),consumerRecord.partition(),
			consumerRecord.offset(),consumerRecord.key());
		showAction(consumerRecord);
	}

}