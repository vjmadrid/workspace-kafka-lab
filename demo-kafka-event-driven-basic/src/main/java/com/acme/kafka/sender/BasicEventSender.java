package com.acme.kafka.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.architecture.event.driven.entity.GenericEvent;

@Service
public class BasicEventSender {

    private static final Logger LOG = LoggerFactory.getLogger(BasicEventSender.class);

    @Value("${app.topic.example1}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, GenericEvent> kafkaTemplate;

    public void send(GenericEvent event){
        LOG.info("[BasicEventSender] sending event='{}' to topic='{}'", event.toString(), topic);
        kafkaTemplate.send(topic, event);
    }
}
