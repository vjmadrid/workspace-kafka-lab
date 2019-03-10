package com.acme.kafka.connector.rest.sender.client;
 
import java.net.URI;
import java.util.Date;

import org.springframework.web.client.RestTemplate;

import com.acme.kafka.connector.rest.sender.constant.KafkaRestSenderConnectorMessageConstant; 

public class KafkaRestSenderConnectorMainClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8081/kafka-connector"+KafkaRestSenderConnectorMessageConstant.MAPPING_MESSAGE;
    
    private static void sendMessage() {
        System.out.println("*** Test send message API ***");
        RestTemplate restTemplate = new RestTemplate();
        String message = "Hello World! "+new Date().getTime();
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI, message, String.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    public static void main(String args[]){
    	sendMessage();
    }
}