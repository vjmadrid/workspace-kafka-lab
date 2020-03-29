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
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.kafka.constant.KafkaConfigConstant;
import com.acme.kafka.consumer.BasicBatchConsumer;
import com.acme.kafka.producer.BasicProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class BasicBatchSenderReceiverTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicBatchSenderReceiverTest.class);
	
	public static final int NUM_BROKERS_START = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@Autowired
	private BasicProducer basicProducer;

	@Autowired
	private BasicBatchConsumer basicBatchConsumer;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@ClassRule //spring-kafka-test
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(NUM_BROKERS_START, true, EXAMPLE_TOPIC);

	@Before
	public void init() throws Exception {

		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaRule.getEmbeddedKafka().getPartitionsPerTopic());
		}
		
	}

	@Test
	public void shouldSendMessage() throws InterruptedException {
		
		int numMessages = KafkaConfigConstant.RECEIVER_COUNTDOWNLATCH;
	    for (int i = 1; i <= numMessages; i++) {
	    	basicProducer.send(TEST_MESSAGE_VALUE + " " + i);
	    }

	    basicBatchConsumer.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		assertThat(basicBatchConsumer.getLatchTest().getCount()).isEqualTo(0);
	}

}
