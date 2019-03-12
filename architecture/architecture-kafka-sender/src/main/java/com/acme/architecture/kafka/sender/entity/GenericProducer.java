package com.acme.architecture.kafka.sender.entity;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.architecture.common.callback.dummy.DummyCallback;


public class GenericProducer <K extends Serializable, V extends Serializable> {

	public static final Logger LOG = LoggerFactory.getLogger(GenericProducer.class);
	
	private KafkaProducer<byte[], byte[]> producer;
    private boolean syncSend;
    private volatile boolean shutDown = false;
    
    public GenericProducer(Properties producerConfig) {
        this(producerConfig, true);
    }

    public GenericProducer(Properties producerConfig, boolean syncSend) {
        this.syncSend = syncSend;
        this.producer = new KafkaProducer<>(producerConfig);
        LOG.info("Started Producer.  sync  : {}", syncSend);
    }
    
    public void send(String topic, V v) {
        send(topic, -1, null, v, new DummyCallback());
    }

    public void send(String topic, K k, V v) {
        send(topic, -1, k, v, new DummyCallback());
    }

    public void send(String topic, int partition, V v) {
        send(topic, partition, null, v, new DummyCallback());
    }

    public void send(String topic, int partition, K k, V v) {
        send(topic, partition, k, v, new DummyCallback());
    }
    
    public void send(String topic, int partition, K key, V value, Callback callback) {
       
    	if (shutDown) {
            throw new RuntimeException("Producer is closed.");
        }

        try {
            ProducerRecord record;
            if(partition < 0)
                record = new ProducerRecord<>(topic, key, value);
            else
                record = new ProducerRecord<>(topic, partition, key, value);


            Future<RecordMetadata> future = producer.send(record, callback);
            if (!syncSend) return;
            future.get();
        } catch (Exception e) {
            LOG.error("Error while producing event for topic : {}", topic, e);
        }

    }
    
    public void close() {
        shutDown = true;
        try {
            producer.close();
        } catch (Exception e) {
            LOG.error("Exception occurred while stopping the producer", e);
        }
    }

	
}
