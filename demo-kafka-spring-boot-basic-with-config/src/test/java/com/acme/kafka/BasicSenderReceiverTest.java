package com.acme.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.kafka.consumer.BasicConsumer;
import com.acme.kafka.producer.BasicProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = BasicSenderReceiverTest.NUM_BROKERS	, topics = {BasicSenderReceiverTest.EXAMPLE_TOPIC})
public class BasicSenderReceiverTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicSenderReceiverTest.class);

	public static final String EXAMPLE_TOPIC = "topic-example";
	
	public static final int NUM_BROKERS = 1;
	
	private String messageTest;
	
	@Autowired
	private BasicProducer basicSender;
	
	@Autowired
	private BasicConsumer basicReceiver;
	
	@Before
	public void init() throws Exception {
		messageTest = "TEST";
	}

	@Test
	public void shouldSend() throws InterruptedException {
		basicSender.send(messageTest);
		
		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
	    assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0);
	}
	
	@Test
	public void shouldSendWithTopic() throws InterruptedException {
		basicSender.send(EXAMPLE_TOPIC,messageTest);
		
		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
	    assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0);
	}

}
