package com.acme.kafka;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.acme.kafka.sender.BasicSender;

@SpringBootApplication
public class SpringKafkaApplication implements CommandLineRunner {

	public static final int NUM_SENDS = 8;
	
    @Autowired
    private BasicSender basicSender;
	
    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    	for(int i = 0; i<NUM_SENDS; i++) {
    		basicSender.send("["+i+"] Hello World! "+new Date());
    	}
    }
}