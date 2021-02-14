package com.acme.kafka.producer;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


public class BasicProducer {
	
	private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";

    private static final String TOPIC = "topic-1";
    
    public static final String MESSAGE_VALUE = "Hello World! "+new Date();
        
    public static void main(String[] args) {

    	// Create properties
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(producerProperties);

        // Create producer record
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(TOPIC, MESSAGE_VALUE);

        // Send data asynchronous
        producer.send(record);

        // Flush data
        producer.flush();
        
        // Flush and close producer
        producer.close();

    }

    
 
}
