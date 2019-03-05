package com.acme.kafka.receiver;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class BasicReceiverTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicReceiverTest.class);

	private static final String EXAMPLE_TOPIC_EVENTS = "topic-events";

	private static final int NUM_BROKERS = 1;

	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(NUM_BROKERS, true, EXAMPLE_TOPIC_EVENTS);

	@Autowired
	private BasicReceiver basicReceiver;

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	private String messageTest;

	private void configSenderKafkaEmbedded() throws Exception {
		LOG.debug("*** configSenderKafkaEmbedded ***");
		LOG.debug("Set up the Kafka producer properties...");
		Map<String, Object> senderProperties = KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());

		LOG.debug("Create a Kafka producer factory...");
		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<String, String>(
				senderProperties);

		LOG.debug("Create a Kafka templaete...");
		kafkaTemplate = new KafkaTemplate<>(producerFactory);
		kafkaTemplate.setDefaultTopic(EXAMPLE_TOPIC_EVENTS);

		LOG.debug("Wait until the container has the required number of assigned partitions...");
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafka.getPartitionsPerTopic());
		}
	}

	@Before
	public void init() throws Exception {
		messageTest = "TEST";

		configSenderKafkaEmbedded();
	}

	@Test
	public void shouldReceive() throws InterruptedException {
		kafkaTemplate.sendDefault(messageTest);
		LOG.debug("shouldSendEvent sent message='{}'", messageTest);
		
		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
	    // check that the message was received
	    assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0);
	}

}
