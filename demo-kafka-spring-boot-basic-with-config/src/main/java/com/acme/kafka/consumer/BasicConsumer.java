package com.acme.kafka.consumer;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.acme.kafka.constant.KafkaConfigConstant;

@Service
public class BasicConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(BasicConsumer.class);

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

	@KafkaListener(id = "basic-listener", topics = "${app.topic.example1}")
	public void receive(@Payload String message, @Headers MessageHeaders headers) {
		LOG.info("[BasicConsumer] received message='{}'", message);

		// Mostrar detalles de la recepción [Opcional]
		LOG.info("[BasicConsumer] details...");
		showMessageHeaders(headers);
		
		// No Usar en Prod -> Avisa de que el mensaje se ha recibido
		LOG.info("[BasicConsumer] latch.countDown()...");
		latchTest.countDown();
	}

}