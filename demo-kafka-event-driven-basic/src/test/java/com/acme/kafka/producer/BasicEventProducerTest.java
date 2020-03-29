package com.acme.kafka.producer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

import java.util.Map;
import java.util.UUID;
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

import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.architecture.event.driven.enumerate.GenericEventTypeEnum;
import com.acme.architecture.event.driven.factory.GenericEventDataFactory;
import com.acme.kafka.producer.BasicEventProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class BasicEventProducerTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicEventProducerTest.class);
	
	public static final int NUM_BROKERS_START = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@ClassRule //spring-kafka-test
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(NUM_BROKERS_START, true, EXAMPLE_TOPIC);

	@Autowired
	private BasicEventProducer basicEventProducer;

	private KafkaMessageListenerContainer<String, String> container;

	private BlockingQueue<ConsumerRecord<String, String>> records;
	
	private GenericEvent eventTest;

	private void setupKafkaReceiverTestEnvironment() throws Exception {
		Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("sender", "false", embeddedKafkaRule.getEmbeddedKafka());

		DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProperties);

		ContainerProperties containerProperties = new ContainerProperties(EXAMPLE_TOPIC);

		container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

		records = new LinkedBlockingQueue<>();

		container.setupMessageListener(new MessageListener<String, String>() {
			@Override
			public void onMessage(ConsumerRecord<String, String> record) {
				LOG.debug("test-listener received message='{}'", record.toString());
				records.add(record);
			}
		});

		container.start();

		ContainerTestUtils.waitForAssignment(container, embeddedKafkaRule.getEmbeddedKafka().getPartitionsPerTopic());
	}

	@Before
	public void init() throws Exception {
		eventTest = GenericEventDataFactory.create(UUID.randomUUID().toString(),"", "Send Message",GenericEventTypeEnum.CREATE.toString(), "", 0, TEST_MESSAGE_VALUE);

		setupKafkaReceiverTestEnvironment();
	}

	@After
	public void tearDown() {
		container.stop();
	}

	@Test
	public void shouldProduce() throws InterruptedException {
		basicEventProducer.send(eventTest);
		
	    ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);
	    
	    // Hamcrest Matchers
	    assertThat(received, hasValue(TEST_MESSAGE_VALUE));
	    
	    // AssertJ Condition to check the key
	   // assertThat(received).has(key(null));
	}

}
