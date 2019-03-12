package com.acme.kafka.receiver;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.acme.kafka.constant.KafkaConfigConstant;
import com.acme.kafka.entity.CustomMessage;

@Service
public class JsonReceiver extends GenericReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(JsonReceiver.class);

	private CountDownLatch latchTest = new CountDownLatch(KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH);

	public CountDownLatch getLatchTest() {
		return latchTest;
	}

	@KafkaListener(id = "json-listener", clientIdPrefix = "json", topics = "${app.topic.example1}", containerFactory = "kafkaListenerJsonContainerFactory")
	public void receiveString(ConsumerRecord<String, CustomMessage> consumerRecord,@Payload CustomMessage customMessage) {
		LOG.info("[JsonReceiver] received message='{}'", customMessage);
		LOG.info("[*] key='{}' | type='{}' | record='{}' ", consumerRecord.key(), typeIdHeader(consumerRecord.headers()), consumerRecord.toString());
		latchTest.countDown();
	}

}