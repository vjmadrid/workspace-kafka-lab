package com.acme.kafka.testing.embeddedkafkarule.advance;

import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.kafka.testing.producer.BasicProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class AdvanceProducerTest {

	public static final Logger LOG = LoggerFactory.getLogger(AdvanceProducerTest.class);
	
	public static final int NUM_BROKERS_START = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@ClassRule //spring-kafka-test
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(NUM_BROKERS_START, true, EXAMPLE_TOPIC);
	
	@Autowired
	private BasicProducer basicProducer;

	private KafkaMessageListenerContainer<String, String> container;

	private BlockingQueue<ConsumerRecord<String, String>> records;

	private void setupKafkaConsumerTestEnvironment() throws Exception {
		LOG.debug("*** SETUP Kafka Consumer Test Environment ***");
		
		LOG.debug(" - Setup Kafka consumer properties");
		Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("sender", "false", embeddedKafkaRule.getEmbeddedKafka());

		LOG.debug(" - Create a Kafka consumer factory");
		DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProperties);

		LOG.debug(" - Set up the container properties with TOPIC :: "+EXAMPLE_TOPIC);
		ContainerProperties containerProperties = new ContainerProperties(EXAMPLE_TOPIC);

		LOG.debug(" - Create a Container with MessageListenerContainer -> topic, ...");
		container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

		LOG.debug(" - Create a thread safe queue to store the received message");
		records = new LinkedBlockingQueue<>();

		LOG.debug(" - Setup a Kafka message listener");
		container.setupMessageListener(new MessageListener<String, String>() {
			@Override
			public void onMessage(ConsumerRecord<String, String> record) {
				LOG.debug("test-listener received message='{}'", record.toString());
				records.add(record);
			}
		});

		LOG.debug(" - Start Container");	
		container.start();

		LOG.debug(" - Wait until the container has the required number of assigned partitions");
		ContainerTestUtils.waitForAssignment(container, embeddedKafkaRule.getEmbeddedKafka().getPartitionsPerTopic());
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("*** SETUP ***");
		
		String kafkaBootstrapServers = embeddedKafkaRule.getEmbeddedKafka().getBrokersAsString();
		System.out.println(" [*] kafkaBootstrapServers :: "+kafkaBootstrapServers);
		
		setupKafkaConsumerTestEnvironment();
	}

	@After
	public void tearDown() {
		container.stop();
	}

	@Test
	public void shouldProduce() throws InterruptedException {
		basicProducer.send(TEST_MESSAGE_VALUE);
		
	    ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
	    
	    // Hamcrest Matchers
	    assertThat(received, hasValue(TEST_MESSAGE_VALUE));
	    
	    // AssertJ Condition to check the key
	    assertThat(received).has(key(null));
	}

}
