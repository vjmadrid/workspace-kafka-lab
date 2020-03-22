package com.acme.kafka;

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

import com.acme.kafka.consumer.BasicConsumer;
import com.acme.kafka.producer.BasicProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class BasicSenderReceiverTest {

	public static final int NUM_BROKERS = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";
	
	@ClassRule
	public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(NUM_BROKERS, true, EXAMPLE_TOPIC);
	
	@Autowired
	private BasicProducer basicSender;
	
	@Autowired
	private BasicConsumer basicReceiver;
	
	@Before
	public void setup() throws Exception {
		//embeddedKafkaRule.getEmbeddedKafka().addTopics(new NewTopic(EXAMPLE_TOPIC, 10, (short) 1));
	
		//String kafkaBootstrapServers = embeddedKafkaRule.getEmbeddedKafka().getBrokersAsString();

		// IMPORTANT: override the property in application.yml
	    //System.setProperty("spring.kafka.bootstrap-servers", kafkaBootstrapServers);
	}

	@Test
	public void whenCallASendMessage_ThenReceiveMessage() throws InterruptedException {
		basicSender.send(TEST_MESSAGE_VALUE);
		
		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		
	    assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0L);
	}

}
