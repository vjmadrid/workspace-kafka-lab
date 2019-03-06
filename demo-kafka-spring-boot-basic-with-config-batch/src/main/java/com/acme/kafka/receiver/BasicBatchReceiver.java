package com.acme.kafka.receiver;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.acme.kafka.constant.KafkaConfigConstant;

@Service
public class BasicBatchReceiver {

	private static final Logger LOG = LoggerFactory.getLogger(BasicBatchReceiver.class);

	private CountDownLatch latchTest = new CountDownLatch(KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH);

	public CountDownLatch getLatchTest() {
		// Indicador que el mensaje es recibido en las test
		return latchTest;
	}

	@KafkaListener(id = "batch-listener", topics = "${app.topic.example1}")
	public void receive(List<String> data, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
			@Header(KafkaHeaders.OFFSET) List<Long> offsets) {

		LOG.info("[BasicBatchReceiver] *** START Batch Receive ***");
		for (int i = 0; i < data.size(); i++) {
			LOG.info("[BasicBatchReceiver] received message='{}' with partition-offset='{}'", data.get(i), partitions.get(i) + "-" + offsets.get(i));
			// handle message
			LOG.info("[BasicBatchReceiver] latch.countDown()...");
			latchTest.countDown();
		}
		LOG.info("[BasicBatchReceiver] *** END Batch Receive ***");
	}

}