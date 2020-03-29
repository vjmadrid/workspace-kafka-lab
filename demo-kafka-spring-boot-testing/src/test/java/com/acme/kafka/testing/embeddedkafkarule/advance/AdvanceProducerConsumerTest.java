package com.acme.kafka.testing.embeddedkafkarule.advance;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;
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

import com.acme.kafka.testing.consumer.BasicConsumer;
import com.acme.kafka.testing.producer.BasicProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class AdvanceProducerConsumerTest {

	public static final int NUM_BROKERS = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@ClassRule
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(NUM_BROKERS, true, EXAMPLE_TOPIC);

	@Autowired
	private BasicProducer basicProducer;

	@Autowired
	private BasicConsumer basicConsumer;

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	private Consumer<Integer, String> consumer;

	private KafkaTemplate<String, String> setUpKafkaTemplateBasic() {
		return new KafkaTemplate<>(
				new DefaultKafkaProducerFactory<>(KafkaTestUtils.producerProps(embeddedKafkaRule.getEmbeddedKafka())));
	}

	private KafkaTemplate<String, String> setUpKafkaTemplateAdvance() {
		
		// SetUp producer properties
		Map<String, Object> producerProperties = KafkaTestUtils.producerProps(embeddedKafkaRule.getEmbeddedKafka());
				
		// Create Kafka producer factory
		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<String, String>(
				producerProperties);

		// Create a Kafka template	
		return new KafkaTemplate<>(producerFactory);
	}
	
	private KafkaConsumer<Integer, String> setUpConsumerBasic() {
		return new KafkaConsumer<Integer, String>( 
                KafkaTestUtils.consumerProps("spring_group", "true", embeddedKafkaRule.getEmbeddedKafka()));
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("*** SETUP ***");
		
		String kafkaBootstrapServers = embeddedKafkaRule.getEmbeddedKafka().getBrokersAsString();
		System.out.println(" [*] kafkaBootstrapServers :: "+kafkaBootstrapServers);

		// Setup kafkaTemplate 
		kafkaTemplate = setUpKafkaTemplateAdvance();
		kafkaTemplate.setDefaultTopic(EXAMPLE_TOPIC);

		// Wait until the partitions are assigned
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer,
					embeddedKafkaRule.getEmbeddedKafka().getPartitionsPerTopic());
		}

//		consumer = prepareConsumerBasic();
//		consumer.subscribe(Collections.singleton(EXAMPLE_TOPIC));
		
		// IMPORTANT: override the property in application.yml
		// System.setProperty("spring.kafka.bootstrap-servers", kafkaBootstrapServers);
	}

	@Test
	public void whenCallASendMessageDefaultWithTemplate_ThenReceiveMessage() throws InterruptedException {
		kafkaTemplate.sendDefault(TEST_MESSAGE_VALUE);

		basicConsumer.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, basicConsumer.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(basicConsumer.getLatchTest().getCount()).isEqualTo(0);
	}
	
	@Test
	public void whenCallASendMessageWithTemplate_ThenReceiveMessage() throws InterruptedException {
		kafkaTemplate.send(EXAMPLE_TOPIC, TEST_MESSAGE_VALUE);

		basicConsumer.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, basicConsumer.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(basicConsumer.getLatchTest().getCount()).isEqualTo(0);
	}
	
	@Test
	public void whenCallASendMessageWithSender_ThenReceiveMessage() throws InterruptedException {
		basicProducer.send(TEST_MESSAGE_VALUE);

		basicConsumer.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, basicConsumer.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(basicConsumer.getLatchTest().getCount()).isEqualTo(0);
	}
	
//	@Test
//	public void whenCallASendMessageDefaultWithTemplateAndConsumer_ThenReceiveMessage() throws InterruptedException {
//		kafkaTemplate.sendDefault(TEST_MESSAGE_VALUE);
//
//		ConsumerRecords<Integer, String> records = KafkaTestUtils.getRecords(consumer);
//        assertThat(records).isNull();
//	}

}
