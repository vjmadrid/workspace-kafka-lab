package com.acme.kafka.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Map;
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
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.kafka.consumer.BasicBatchConsumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class BasicConsumerTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicConsumerTest.class);

	public static final int NUM_BROKERS_START = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@ClassRule //spring-kafka-test
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(NUM_BROKERS_START, true, EXAMPLE_TOPIC);

	@Autowired
	private BasicBatchConsumer basicBatchConsumer;

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	private void setupKafkaProducerTestEnvironment() throws Exception {
		Map<String, Object> senderProperties = KafkaTestUtils.producerProps(embeddedKafkaRule.getEmbeddedKafka());

		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<String, String>(
				senderProperties);

		kafkaTemplate = new KafkaTemplate<>(producerFactory);
		kafkaTemplate.setDefaultTopic(EXAMPLE_TOPIC);

		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaRule.getEmbeddedKafka().getPartitionsPerTopic());
		}
	}

	@Before
	public void setUp() throws Exception {
		setupKafkaProducerTestEnvironment();
	}

	@Test
	public void shouldConsume() throws InterruptedException {
		kafkaTemplate.sendDefault(TEST_MESSAGE_VALUE);
		LOG.debug("shouldSendEvent sent message='{}'", TEST_MESSAGE_VALUE);
		
		basicBatchConsumer.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, basicBatchConsumer.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(basicBatchConsumer.getLatchTest().getCount()).isEqualTo(0);
	}

}
