package com.acme.kafka.receiver;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.kafka.constant.KafkaConfigConstant;

@Service
public class BasicEventReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(BasicEventReceiver.class);

	private CountDownLatch latchTest = new CountDownLatch(KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH);

	public CountDownLatch getLatchTest() {
		// Indicador que el mensaje es recibido en las test
		return latchTest;
	}
	
	private void showMessageHeaders(MessageHeaders headers) {
		if (headers != null) {
			headers.keySet().forEach(key -> LOG.info("{}: {}", key, headers.get(key)));
		}
	}

	@KafkaListener(id = "event-listener", topics = "${app.topic.example1}")
	public void receive(@Payload GenericEvent event, @Headers MessageHeaders headers) {
		LOG.info("[BasicEventReceiver] received event='{}'", event.toString());
		LOG.info("[BasicEventReceiver] id='{}'", event.getId());
		
		// Mostrar detalles de la recepción
		LOG.info("[BasicEventReceiver] details...");
		showMessageHeaders(headers);

		// No use in production environment
		LOG.info("[BasicEventReceiver] latch.countDown()...");
		latchTest.countDown();
	}

}