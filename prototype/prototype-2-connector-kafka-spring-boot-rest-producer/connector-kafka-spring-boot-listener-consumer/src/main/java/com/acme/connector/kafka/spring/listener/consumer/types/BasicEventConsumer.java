package com.acme.connector.kafka.spring.listener.consumer.types;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.architecture.event.driven.util.converter.GenericEventConverter;

@Service
public class BasicEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(BasicEventConsumer.class);

    @KafkaListener(topics = "${app.topic.events}")
    public void receive(@Payload String jsonEvent, @Headers MessageHeaders headers) {
        LOG.info("[BasicEventConsumer] received event='{}'", jsonEvent);
        
        // Mostrar detalles de la recepción
        LOG.info("[BasicEventConsumer] details...");
        headers.keySet().forEach(key -> 
        	LOG.info("{}: {}", key, headers.get(key))
        );
        
        try {
			GenericEvent event = GenericEventConverter.fromJsonToGenericEvent(jsonEvent);
			LOG.info("[*]  event='{}'", event);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }

}