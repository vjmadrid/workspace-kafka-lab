package com.acme.kafka.testing.annotation;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.kafka.testing.consumer.BasicConsumer;
import com.acme.kafka.testing.producer.BasicProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = BasicProducerConsumerEmbeddedAnnotationTest.NUM_PARTITIONS, topics = {
		BasicProducerConsumerEmbeddedAnnotationTest.EXAMPLE_TOPIC })
public class BasicProducerConsumerEmbeddedAnnotationTest {

	public static final int NUM_PARTITIONS = 1;
	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";

	@Autowired
	private BasicProducer basicProducer;

	@Autowired
	private BasicConsumer basicConsumer;

	@Before
	public void setup() throws Exception {
		
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
