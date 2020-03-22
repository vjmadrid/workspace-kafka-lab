package com.acme.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@EmbeddedKafka(partitions = BasicSenderReceiverEmbeddedAnnotationTest.NUM_PARTITIONS, topics = {
		BasicSenderReceiverEmbeddedAnnotationTest.EXAMPLE_TOPIC })
public class BasicSenderReceiverEmbeddedAnnotationTest {

	public static final int NUM_PARTITIONS = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@Autowired
	private BasicProducer basicSender;

	@Autowired
	private BasicConsumer basicReceiver;

	@Before
	public void setup() throws Exception {
	}

	@Test
	public void whenCallASendMessage_ThenReceiveMessage() throws InterruptedException {
		basicSender.send(TEST_MESSAGE_VALUE);

		basicReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		assertThat(basicReceiver.getLatchTest().getCount()).isEqualTo(0);
	}

}
