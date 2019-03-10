package com.acme.kafka.connector.listener.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaListenerReceiverConnectorApplication{

	public static void main(String[] args) {
        SpringApplication.run(KafkaListenerReceiverConnectorApplication.class, args);
    }

}