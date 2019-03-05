package com.acme.kafka.connector.rest.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaRestSenderConnectorApplication {

  public static void main(String[] args) {
    SpringApplication.run(KafkaRestSenderConnectorApplication.class, args);
  }
}
