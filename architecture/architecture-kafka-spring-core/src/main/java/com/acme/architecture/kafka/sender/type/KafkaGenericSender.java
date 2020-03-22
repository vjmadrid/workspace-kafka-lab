package com.acme.architecture.kafka.sender.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service("kafkaGenericSender")
public class KafkaGenericSender {
	
	public static final Logger LOG = LoggerFactory.getLogger(KafkaGenericSender.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void send(String topic, String data) {
		LOG.info("[KafkaGenericSender] Sending data=['{}'] to topic=['{}']", data, topic);
	    kafkaTemplate.send(topic, data);
	}

}
