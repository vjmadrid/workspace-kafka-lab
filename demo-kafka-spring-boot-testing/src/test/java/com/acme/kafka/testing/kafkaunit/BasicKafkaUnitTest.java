package com.acme.kafka.testing.kafkaunit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.acme.kafka.testing.embeddedkafkarule.advance.AdvanceProducerTest;
import com.github.charithe.kafka.EphemeralKafkaBroker;
import com.github.charithe.kafka.KafkaHelper;
import com.github.charithe.kafka.KafkaJunitExtension;
import com.github.charithe.kafka.KafkaJunitExtensionConfig;
import com.github.charithe.kafka.KafkaJunitRule;
import com.github.charithe.kafka.StartupMode;


@Ignore
@ExtendWith(KafkaJunitExtension.class)
@KafkaJunitExtensionConfig(startupMode = StartupMode.WAIT_FOR_STARTUP)
public class BasicKafkaUnitTest {
	
	public static final Logger LOG = LoggerFactory.getLogger(BasicKafkaUnitTest.class);

	public static final String EXAMPLE_TOPIC = "topic-1";
	public static final String TEST_MESSAGE_VALUE = "Test Message!";
	
	@ClassRule
	public static KafkaJunitRule kafkaJunitRule = new KafkaJunitRule(EphemeralKafkaBroker.create());
	
	KafkaProducer<String, String> producer;
	
	KafkaConsumer<String, String> consumer;
	
	@Before
	public void setup() throws Exception {
		System.out.println("*** SETUP ***");
		
		LOG.debug(" - Create Producer");
		producer = kafkaJunitRule.helper().createStringProducer();

		LOG.debug(" - Create Consumer");
		consumer = kafkaJunitRule.helper().createStringConsumer();
	}

	@Test
    public void testKafkaServerIsUp() {
        try (KafkaProducer<String, String> producer = kafkaJunitRule.helper().createStringProducer()) {
            producer.send(new ProducerRecord<>(EXAMPLE_TOPIC, "keyA", "valueA"));
        }

        try (KafkaConsumer<String, String> consumer = kafkaJunitRule.helper().createStringConsumer()) {
        	List<String> TOPIC_LIST =  Arrays.asList(EXAMPLE_TOPIC);
        	
            consumer.subscribe(TOPIC_LIST);
            
            ConsumerRecords<String, String> records = consumer.poll(10000);
            assertThat(records).isNotNull();
            assertThat(records.isEmpty()).isFalse();

            ConsumerRecord<String, String> msg = records.iterator().next();
            assertThat(msg).isNotNull();
            assertThat(msg.key()).isEqualTo("keyA");
            assertThat(msg.value()).isEqualTo("valueA");
        }
    }
	
//	@Test
//	public void whenCallASendMessage_ThenReceiveMessage(KafkaHelper kafkaHelper) throws InterruptedException, ExecutionException {
//		String kafkaBootstrapServers = kafkaHelper.producerConfig().get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG).toString();
//		System.out.println(" [*] kafkaBootstrapServers :: "+kafkaBootstrapServers);
//		
//		kafkaJunitRule.helper().produceStrings(EXAMPLE_TOPIC, TEST_MESSAGE_VALUE, TEST_MESSAGE_VALUE);
//	    List<String> result = kafkaJunitRule.helper().consumeStrings(EXAMPLE_TOPIC, 2).get();
//	    
//	   
//	    
//	    String zkConnStr = kafkaJunitRule.helper().zookeeperConnectionString();
//	    int brokerPort = kafkaJunitRule.helper().kafkaPort();
//		
//	}

}
