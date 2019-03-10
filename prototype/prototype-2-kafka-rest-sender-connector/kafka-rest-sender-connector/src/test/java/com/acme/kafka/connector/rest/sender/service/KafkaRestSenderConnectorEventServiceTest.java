package com.acme.kafka.connector.rest.sender.service;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.test.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class KafkaRestSenderConnectorEventServiceTest {

	public static final Logger LOG = LoggerFactory.getLogger(KafkaRestSenderConnectorEventServiceImpl.class);

	private static final String EXAMPLE_TOPIC_EVENTS = "topic-events";
	
	private static final int NUM_BROKERS = 1;

	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(NUM_BROKERS, true, EXAMPLE_TOPIC_EVENTS);

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@Autowired
	private KafkaRestSenderConnectorEventService kafkaRestSenderConnectorEventService;

	private KafkaMessageListenerContainer<String, String> container;

	private BlockingQueue<ConsumerRecord<String, String>> records;

	private KafkaTemplate<String, String> kafkaTemplate;

	private String messageTest;

	private void configSenderKafkaEmbedded() throws Exception {
		// Set up the Kafka producer properties
		Map<String, Object> senderProperties = KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());

		// Create a Kafka ProducerFactory
		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<String, String>(
				senderProperties);

		// Create a Kafka template
		kafkaTemplate = new KafkaTemplate<>(producerFactory);
		kafkaTemplate.setDefaultTopic(EXAMPLE_TOPIC_EVENTS);

		// Wait until the partitions are assigned
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafka.getPartitionsPerTopic());
		}
	}

	private void configReceiverKafkaEmbedded() throws Exception {
		// Set up the Kafka consumer properties
		Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("sender", "false", embeddedKafka);

		// create a Kafka consumer factory
		DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProperties);

		// Set the topic that needs to be consumed
		ContainerProperties containerProperties = new ContainerProperties(EXAMPLE_TOPIC_EVENTS);

		// Create a Kafka MessageListenerContainer
		container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

		// Create a thread safe queue to store the received message
		records = new LinkedBlockingQueue<>();

		// Setup a Kafka message listener
		container.setupMessageListener(new MessageListener<String, String>() {
			@Override
			public void onMessage(ConsumerRecord<String, String> record) {
				LOG.debug("test-listener received message='{}'", record.toString());
				records.add(record);
			}
		});

		// start the container and underlying message listener
		container.start();

		// wait until the container has the required number of assigned partitions
		ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
	}

	@Before
	public void init() throws Exception {
		messageTest = "TEST";

		configReceiverKafkaEmbedded();
	}

	@After
	public void tearDown() {
		// stop the container
		container.stop();
		
	}

	@Test
	public void shouldSendEvent() throws InterruptedException {
		String result = kafkaRestSenderConnectorEventService.sendEvent(messageTest);
		
		// check that the message was received
	    ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
	    
	    assertTrue(result.contains(messageTest));
	    
	    // Hamcrest Matchers to check the value
	    //assertThat(received, hasValue(messageTest));
	    // AssertJ Condition to check the key
	    //assertThat(received).has(key(null));
	}

}
