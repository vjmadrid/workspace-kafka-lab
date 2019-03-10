package com.acme.kafka.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.kafka.demo.processor.KafkaTopic1Processor;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static final int NUM_SENDS = 1;
	
	@Autowired
    private KafkaTopic1Processor kafkaTopic1Processor;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    	for(int i = 0; i<NUM_SENDS; i++) {
    		kafkaTopic1Processor.send("Hello World! "+new Date().getTime());
    	}
    }
    
}