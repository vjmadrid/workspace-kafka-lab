package com.acme.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.kafka.constant.KafkaConfigConstant;
import com.acme.kafka.receiver.BasicBatchReceiver;
import com.acme.kafka.sender.BasicSender;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class BasicBatchSenderReceiverTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicBatchSenderReceiverTest.class);

	public static final String EXAMPLE_TOPIC = "topic-example";

	public static final int NUM_BROKERS = 1;

	private String messageTest;

	@Autowired
	private BasicSender basicSender;

	@Autowired
	private BasicBatchReceiver basicBatchReceiver;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(NUM_BROKERS, true, EXAMPLE_TOPIC);

	@Before
	public void init() throws Exception {
		messageTest = "TEST";

		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafka.getPartitionsPerTopic());
		}
		
	}

	@Test
	public void shouldSendMessage() throws InterruptedException {
		
		int numMessages = KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH;
	    for (int i = 0; i < numMessages; i++) {
	    	basicSender.send(messageTest + " " + i);
	    }

	    basicBatchReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		assertThat(basicBatchReceiver.getLatchTest().getCount()).isEqualTo(0);
	}

}
