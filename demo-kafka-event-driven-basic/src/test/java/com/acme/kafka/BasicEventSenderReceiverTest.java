package com.acme.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
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
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.architecture.event.driven.enumerate.GenericEventTypeEnum;
import com.acme.architecture.event.driven.factory.GenericEventDataFactory;
import com.acme.kafka.receiver.BasicEventReceiver;
import com.acme.kafka.sender.BasicEventSender;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
//@EmbeddedKafka(partitions = BasicEventSenderReceiverTest.NUM_BROKERS, topics = {
//		BasicEventSenderReceiverTest.EXAMPLE_TOPIC })
public class BasicEventSenderReceiverTest {

	public static final Logger LOG = LoggerFactory.getLogger(BasicEventSenderReceiverTest.class);

	public static final String EXAMPLE_TOPIC = "topic-example";

	public static final int NUM_BROKERS = 1;
	
	@ClassRule
	public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(NUM_BROKERS, true, EXAMPLE_TOPIC);
	
	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

	@Autowired
	private BasicEventSender basicEventSender;

	@Autowired
	private BasicEventReceiver basicEventReceiver;
	
	private String messageTest;

	private GenericEvent eventTest;

	@Before
	public void init() throws Exception {
		messageTest = "TEST";
		eventTest = GenericEventDataFactory.create(UUID.randomUUID().toString(), "", "Send Message",
				GenericEventTypeEnum.CREATE.toString(), "", 0, messageTest);

		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafka.getPartitionsPerTopic());
		}
	}

	@Test
	public void shouldSendMessage() throws InterruptedException {
		basicEventSender.send(eventTest);

		basicEventReceiver.getLatchTest().await(10000, TimeUnit.MILLISECONDS);
		assertThat(basicEventReceiver.getLatchTest().getCount()).isEqualTo(0);
	}

}
