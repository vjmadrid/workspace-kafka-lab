package com.acme.connector.kafka.spring.listener.consumer.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class BasicMessageConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(BasicMessageConsumer.class);

    @KafkaListener(topics = "${app.topic.messages}")
    public void receiveMessage(@Payload String message, @Headers MessageHeaders headers) {
        LOG.info("[BasicMessageConsumer] received message='{}'", message);
        
        // Mostrar detalles de la recepción
        LOG.info("[BasicMessageConsumer] details...");
        headers.keySet().forEach(key -> 
        	LOG.info("{}: {}", key, headers.get(key))
        );
    }

}