package com.acme.kafka.receiver;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.acme.kafka.constant.KafkaConfigConstant;

@Service
public class StringReceiver extends GenericReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(StringReceiver.class);

	private CountDownLatch latchTest = new CountDownLatch(KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH);

	public CountDownLatch getLatchTest() {
		return latchTest;
	}

	@KafkaListener(id = "string-listener", clientIdPrefix = "string", topics = "${app.topic.example1}", containerFactory = "kafkaListenerContainerFactory")
	public void receiveString(ConsumerRecord<String, String> consumerRecord,@Payload String message) {
		LOG.info("[StringReceiver] received message='{}'", message);
		LOG.info("[*] key='{}' | type='{}' | record='{}' ", consumerRecord.key(), typeIdHeader(consumerRecord.headers()), consumerRecord.toString() );
		latchTest.countDown();
	}

}