package com.acme.kafka;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.architecture.event.driven.entity.GenericEvent;
import com.acme.architecture.event.driven.enumerate.GenericEventTypeEnum;
import com.acme.architecture.event.driven.factory.GenericEventDataFactory;
import com.acme.kafka.producer.BasicEventProducer;

@SpringBootApplication
public class SpringKafkaApplication implements CommandLineRunner {
	
	public static final String MESSAGE_VALUE = "Hello World! "+new Date();

    @Autowired
    private BasicEventProducer basicEventProducer;
	
    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    	GenericEvent event = GenericEventDataFactory.create(UUID.randomUUID().toString(),"", "Send Message",GenericEventTypeEnum.CREATE.toString(), "", 0, MESSAGE_VALUE);

    	basicEventProducer.send(event);
    }
}