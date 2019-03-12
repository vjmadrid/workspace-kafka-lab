package com.acme.architecture.kafka.sender.service;

import java.util.concurrent.ExecutionException;

public interface SenderService {
	
	void sendAsync(String data);
	
	void sendSync(String data) throws InterruptedException, ExecutionException;
 
}
