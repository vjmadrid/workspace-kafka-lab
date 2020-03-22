package com.acme.architecture.kafka.sender.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import com.acme.architecture.common.callback.CustomCallback;
import com.acme.architecture.kafka.sender.service.SenderService;

//@Service("senderService")
public class SenderServiceImpl<K, V> implements SenderService {
	
	public static final Logger LOG = LoggerFactory.getLogger(SenderServiceImpl.class);
	
	@Value("${kafka.topic}")
	private String kafkaTopic;
	
	@Autowired
	private ProducerFactory producerStringFactory;
	
	public void sendAsync(String data) {
		//Provide a Callback
		LOG.info("[SERVICE] Send ASYNC Producer message=[{}]",data);
		final long startTime = System.currentTimeMillis();
		final int messageNum = 1;
		
		final Producer<String, String> producer = producerStringFactory.createProducer();
		// GENERIC :: final ProducerRecord producerRecord = new ProducerRecord<K, V>(kafkaTopic,(V) data);
		final ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(kafkaTopic,data);
		Future<RecordMetadata> metadata = producer.send(producerRecord,  new CustomCallback(startTime, messageNum, data));
	}
	
	public void sendSync(String data) throws InterruptedException, ExecutionException {
		//A new message is sent only after completing the previous messessage
		//Can to exist interruption o stopagge in the transmisión
		LOG.info("[SERVICE] Send SYNC Producer message=[{}]",data);
		final Producer<String, String> producer = producerStringFactory.createProducer();
		// GENERIC :: final ProducerRecord producerRecord = new ProducerRecord<K, V>(kafkaTopic,(V) data);
		final ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(kafkaTopic,data);
		RecordMetadata metadata = producer.send(producerRecord).get();
		System.out.println("Record sent with data " + data + " to partition " + metadata.partition()
				+ " with offset " + metadata.offset());
	}

	
	
	
}