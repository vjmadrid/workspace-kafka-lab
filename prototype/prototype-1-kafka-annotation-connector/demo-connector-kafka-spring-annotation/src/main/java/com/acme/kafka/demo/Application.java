package com.acme.kafka.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.kafka.demo.processor.KafkaTopic1Processor;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static final int NUM_MESSAGES = 1;
	public static final String MESSAGE_VALUE = "Hello World! "+new Date().getTime();
	
	@Autowired
    private KafkaTopic1Processor kafkaTopic1Processor;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    	
    	for(int i = 1; i<=NUM_MESSAGES; i++) {
    		kafkaTopic1Processor.send(MESSAGE_VALUE);
    	}
    	
    }
    
}