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
public class ByteArrayReceiver extends GenericReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(ByteArrayReceiver.class);

	private CountDownLatch latchTest = new CountDownLatch(KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH);

	public CountDownLatch getLatchTest() {
		return latchTest;
	}

	@KafkaListener(id = "byte-array-listener", clientIdPrefix = "bytearray", topics = "${app.topic.example1}", containerFactory = "kafkaListenerByteArrayContainerFactory")
	public void receiveString(ConsumerRecord<String, byte[]> consumerRecord,@Payload byte[] payload) {
		LOG.info("[ByteArrayReceiver] received message='{}'", payload);
		LOG.info("[*] key='{}' | type='{}' | record='{}' ", consumerRecord.key(), typeIdHeader(consumerRecord.headers()), consumerRecord.toString() );
		latchTest.countDown();
	}

}