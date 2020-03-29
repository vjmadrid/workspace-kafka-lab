package com.acme.kafka.testing.embeddedkafkarule.advance;

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

import com.acme.kafka.testing.consumer.BasicConsumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class AdvanceConsumerTest {

	public static final Logger LOG = LoggerFactory.getLogger(AdvanceConsumerTest.class);

	public static final int NUM_BROKERS_START = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@ClassRule // spring-kafka-test
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(NUM_BROKERS_START, true, EXAMPLE_TOPIC);

	@Autowired
	private BasicConsumer basicConsumer;

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	private void setupKafkaProducerTestEnvironment() throws Exception {
		LOG.debug("*** SETUP Kafka Producer Test Environment ***");

		LOG.debug(" - SetUp Kafka producer properties");
		Map<String, Object> senderProperties = KafkaTestUtils.producerProps(embeddedKafkaRule.getEmbeddedKafka());

		LOG.debug(" - Create a Kafka producer factory");
		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<String, String>(
				senderProperties);

		LOG.debug(" - Create a Kafka template");
		kafkaTemplate = new KafkaTemplate<>(producerFactory);
		kafkaTemplate.setDefaultTopic(EXAMPLE_TOPIC);

		LOG.debug(" - Wait until the container has the required number of assigned partitions");
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer,
					embeddedKafkaRule.getEmbeddedKafka().getPartitionsPerTopic());
		}
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("*** SETUP ***");

		String kafkaBootstrapServers = embeddedKafkaRule.getEmbeddedKafka().getBrokersAsString();
		System.out.println(" [*] kafkaBootstrapServers :: " + kafkaBootstrapServers);

		setupKafkaProducerTestEnvironment();
	}

	@Test
	public void shouldConsume() throws InterruptedException {
		kafkaTemplate.sendDefault(TEST_MESSAGE_VALUE);
		LOG.debug("should sent message='{}'", TEST_MESSAGE_VALUE);

		basicConsumer.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, basicConsumer.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(basicConsumer.getLatchTest().getCount()).isEqualTo(0);
	}

}
