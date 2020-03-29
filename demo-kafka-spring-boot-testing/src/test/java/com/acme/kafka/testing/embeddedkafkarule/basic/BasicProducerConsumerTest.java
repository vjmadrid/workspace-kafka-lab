package com.acme.kafka.testing.embeddedkafkarule.basic;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.kafka.testing.consumer.BasicConsumer;
import com.acme.kafka.testing.producer.BasicProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class BasicProducerConsumerTest {

	public static final int NUM_BROKERS_START = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";
	
	@ClassRule //spring-kafka-test
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(NUM_BROKERS_START, true, EXAMPLE_TOPIC);
	
	@Autowired
	private BasicProducer basicProducer;
	
	@Autowired
	private BasicConsumer basicConsumer;
	
	@Before
	public void setup() throws Exception {
		System.out.println("*** SETUP ***");
		
		String kafkaBootstrapServers = embeddedKafkaRule.getEmbeddedKafka().getBrokersAsString();
		System.out.println(" [*] kafkaBootstrapServers :: "+kafkaBootstrapServers);
		
		//embeddedKafkaRule.getEmbeddedKafka().addTopics(new NewTopic(EXAMPLE_TOPIC, 10, (short) 1));
	
		// IMPORTANT: override the property in application.yml
	    //System.setProperty("spring.kafka.bootstrap-servers", kafkaBootstrapServers);
	}

	@Test
	public void whenCallASendMessage_ThenReceiveMessage() throws InterruptedException {
		basicProducer.send(TEST_MESSAGE_VALUE);
		
		basicConsumer.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
		//Junit
	    assertEquals(0L, basicConsumer.getLatchTest().getCount());
	    
	    //AssertJ
	    assertThat(basicConsumer.getLatchTest().getCount()).isEqualTo(0);
	}

}
