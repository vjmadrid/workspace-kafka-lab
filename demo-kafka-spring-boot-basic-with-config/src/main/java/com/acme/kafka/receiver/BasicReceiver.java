package com.acme.kafka.receiver;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class BasicReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(BasicReceiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    //Indicador que el mensaje es recibido en las test
    public CountDownLatch getLatchTest() {
      return latch;
    }
    
    // @KafkaListener : Crea un @KafkaListener
    @KafkaListener(topics = "${app.topic.example1}")
    public void receive(@Payload String message, @Headers MessageHeaders headers) {
        LOG.info("[BasicReceiver] received message='{}'", message);
        
        // Mostrar detalles de la recepción
        LOG.info("[BasicReceiver] details...");
        headers.keySet().forEach(key -> 
        	LOG.info("{}: {}", key, headers.get(key))
        );
        
        //No Usar en Prod
        LOG.info("[BasicReceiver] latch.countDown()...");
        latch.countDown();
    }

}