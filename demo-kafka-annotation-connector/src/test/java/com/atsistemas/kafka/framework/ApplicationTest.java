package com.atsistemas.kafka.framework;

import static org.junit.Assert.assertNotNull;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext

// TEMNPORAL
@Ignore

public class ApplicationTest {

    private static final String TEST_TOPIC = "demo-topic-1";

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1,true,TEST_TOPIC);

    @Test
    public void shouldStartEmbeddedKafka() throws Exception {
    	assertNotNull(embeddedKafka);
    }

}
