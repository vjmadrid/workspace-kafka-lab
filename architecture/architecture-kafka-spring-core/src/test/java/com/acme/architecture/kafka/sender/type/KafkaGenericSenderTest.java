package com.acme.architecture.kafka.sender.type;

import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.architecture.kafka.sender.config.SpringConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=SpringConfig.class)
@DirtiesContext
public class KafkaGenericSenderTest {

	public static final Logger LOG = LoggerFactory.getLogger(KafkaGenericSenderTest.class);

	private static final String EXAMPLE_TOPIC = "topic-example";
	
	private static final int NUM_BROKERS = 1;
	
	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(NUM_BROKERS, true, EXAMPLE_TOPIC);
	
	@Autowired
	@Qualifier("kafkaGenericSender")
	private KafkaGenericSender kafkaGenericSender;

	private KafkaMessageListenerContainer<String, String> container;

	private BlockingQueue<ConsumerRecord<String, String>> records;

	private String messageTest;

	private void configReceiverKafkaEmbedded() throws Exception {
		LOG.debug("*** configReceiverKafkaEmbedded ***");
		LOG.debug("Set up the Kafka consumer properties...");
		Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("sender", "false", embeddedKafka);

		LOG.debug("Create a Kafka consumer factory...");
		DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProperties);

		LOG.debug("Set up the container properties...");
		ContainerProperties containerProperties = new ContainerProperties(EXAMPLE_TOPIC);

		LOG.debug("Create a Container with MessageListenerContainer -> topic...");
		container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

		LOG.debug("Create a thread safe queue to store the received message...");
		records = new LinkedBlockingQueue<>();

		LOG.debug("Setup a Kafka message listener...");
		container.setupMessageListener(new MessageListener<String, String>() {
			@Override
			public void onMessage(ConsumerRecord<String, String> record) {
				LOG.debug("test-listener received message='{}'", record.toString());
				records.add(record);
			}
		});

		container.start();

		LOG.debug("Wait until the container has the required number of assigned partitions...");
		ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
	}

	@Before
	public void init() throws Exception {
		messageTest = "TEST";

		configReceiverKafkaEmbedded();
	}

	@After
	public void tearDown() {
		container.stop();
	}

	@Test
	public void shouldSend() throws InterruptedException {
		kafkaGenericSender.send(EXAMPLE_TOPIC,messageTest);
		
	    ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
	    // Hamcrest Matchers
	    assertThat(received, hasValue(messageTest));
	}

}
