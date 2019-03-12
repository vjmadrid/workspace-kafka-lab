package com.acme.kafka.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.kafka.entity.CustomMessage;

@Service
public class BasicSender {

    private static final Logger LOG = LoggerFactory.getLogger(BasicSender.class);

    @Value("${app.topic.example1}")
    private String topic;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(CustomMessage customMessage){
        LOG.info("[BasicSender]  sending customMessage='{}' to topic='{}'", customMessage.toString(), topic);
        kafkaTemplate.send(topic,String.valueOf(customMessage.getId()), customMessage);
    }
    
    public void send(String topicParameter, CustomMessage customMessage) {
    	LOG.info("[BasicSender]  sending customMessage='{}' to topic parameter='{}'", customMessage.toString(), topicParameter);
        kafkaTemplate.send(topicParameter,String.valueOf(customMessage.getId()), customMessage);
    }
    
}
