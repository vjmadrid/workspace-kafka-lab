package com.acme.kafka.connector.listener.receiver.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class BasicMessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(BasicMessageReceiver.class);

    @KafkaListener(topics = "${app.topic.messages}")
    public void receiveMessage(@Payload String message, @Headers MessageHeaders headers) {
        LOG.info("[BasicMessageReceiver] received message='{}'", message);
        
        // Mostrar detalles de la recepción
        LOG.info("[BasicMessageReceiver] details...");
        headers.keySet().forEach(key -> 
        	LOG.info("{}: {}", key, headers.get(key))
        );
    }

}