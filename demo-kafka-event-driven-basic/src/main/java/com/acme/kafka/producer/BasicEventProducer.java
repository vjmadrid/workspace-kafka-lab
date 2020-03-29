package com.acme.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.architecture.event.driven.entity.GenericEvent;

@Service
public class BasicEventProducer {

    private static final Logger LOG = LoggerFactory.getLogger(BasicEventProducer.class);

    @Value("${app.topic.example1}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, GenericEvent> kafkaTemplate;

    public void send(GenericEvent event){
        LOG.info("[BasicEventProducer] sending event='{}' to topic='{}'", event.toString(), topic);
        kafkaTemplate.send(topic, event);
    }
}
