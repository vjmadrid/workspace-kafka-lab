package com.acme.kafka.receiver;

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

import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.kafka.consumer.BasicEventConsumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class BasicEventConsumerTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicEventConsumerTest.class);

	public static final int NUM_BROKERS_START = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@ClassRule //spring-kafka-test
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(NUM_BROKERS_START, true, EXAMPLE_TOPIC);

	@Autowired
	private BasicEventConsumer basicEventConsumer;

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	private GenericEvent eventTest;

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
	public void init() throws Exception {
		setupKafkaProducerTestEnvironment();
	}

	@Test
	public void shouldConsume() throws InterruptedException {
		kafkaTemplate.sendDefault(TEST_MESSAGE_VALUE);
		LOG.debug("shouldSendEvent sent message='{}'", TEST_MESSAGE_VALUE);
		
		basicEventConsumer.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, basicEventConsumer.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(basicEventConsumer.getLatchTest().getCount()).isEqualTo(0);
	}

}
