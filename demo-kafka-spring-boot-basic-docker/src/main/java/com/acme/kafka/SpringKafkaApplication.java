package com.acme.kafka;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.kafka.entity.CustomMessage;
import com.acme.kafka.factory.CustomMessageFactory;
import com.acme.kafka.sender.BasicSender;

@SpringBootApplication
public class SpringKafkaApplication implements CommandLineRunner {

    @Autowired
    private BasicSender basicSender;
	
    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    	CustomMessage customMessage = CustomMessageFactory.create(1, "Hello World! "+new Date());
 
    	basicSender.send(customMessage);
    }
}