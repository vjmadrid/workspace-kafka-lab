package com.acme.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.acme.kafka.consumer.BasicConsumer;
import com.acme.kafka.producer.BasicProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class AdvanceSenderReceiverTest {

	public static final int NUM_BROKERS = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@ClassRule
	public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(NUM_BROKERS, true, EXAMPLE_TOPIC);

	@Autowired
	private BasicProducer basicSender;

	@Autowired
	private BasicConsumer basicReceiver;

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	private Consumer<Integer, String> consumer;

	private KafkaTemplate<String, String> prepareKafkaTemplateBasic() {
		return new KafkaTemplate<>(
				new DefaultKafkaProducerFactory<>(KafkaTestUtils.producerProps(embeddedKafka.getEmbeddedKafka())));
	}

	private KafkaTemplate<String, String> prepareKafkaTemplateAdvance() {
		//String kafkaBootstrapServers = embeddedKafka.getEmbeddedKafka().getBrokersAsString();

		// Setup producer properties
		Map<String, Object> producerProperties = KafkaTestUtils
				.producerProps(embeddedKafka.getEmbeddedKafka().getBrokersAsString());

		// Create Kafka producer factory
		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<String, String>(
				producerProperties);

		// Create a Kafka template	
		return new KafkaTemplate<>(producerFactory);
	}
	
	private KafkaConsumer<Integer, String> prepareConsumerBasic() {
		return new KafkaConsumer<Integer, String>( 
                KafkaTestUtils.consumerProps("spring_group", "true", embeddedKafka.getEmbeddedKafka()));
	}

	@Before
	public void setup() throws Exception {

		// Setup kafkaTemplate 
		kafkaTemplate = prepareKafkaTemplateAdvance();
		kafkaTemplate.setDefaultTopic(EXAMPLE_TOPIC);

		// Wait until the partitions are assigned
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer,
					embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
		}

//		consumer = prepareConsumerBasic();
//		consumer.subscribe(Collections.singleton(EXAMPLE_TOPIC));
		
		// IMPORTANT: override the property in application.yml
		// System.setProperty("spring.kafka.bootstrap-servers", kafkaBootstrapServers);
	}

	@Test
	public void whenCallASendMessageDefaultWithTemplate_ThenReceiveMessage() throws InterruptedException {
		kafkaTemplate.sendDefault(TEST_MESSAGE_VALUE);

		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0);   
	}
	
	@Test
	public void whenCallASendMessageWithTemplate_ThenReceiveMessage() throws InterruptedException {
		kafkaTemplate.send(EXAMPLE_TOPIC, TEST_MESSAGE_VALUE);

		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0);
	}
	
	@Test
	public void whenCallASendMessageWithSender_ThenReceiveMessage() throws InterruptedException {
		basicSender.send(TEST_MESSAGE_VALUE);

		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0);
	}
	
//	@Test
//	public void whenCallASendMessageDefaultWithTemplateAndConsumer_ThenReceiveMessage() throws InterruptedException {
//		kafkaTemplate.sendDefault(TEST_MESSAGE_VALUE);
//
//		ConsumerRecords<Integer, String> records = KafkaTestUtils.getRecords(consumer);
//        assertThat(records).isNull();
//	}

}
