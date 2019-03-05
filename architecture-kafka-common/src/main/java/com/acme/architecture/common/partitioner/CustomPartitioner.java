package com.acme.architecture.common.partitioner;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import com.acme.architecture.kafka.common.constant.GlobalKafkaConstant;

public class CustomPartitioner implements Partitioner{

	@Override
	public void configure(Map<String, ?> configs) {
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		Integer keyInt=Integer.parseInt(key.toString());
		return keyInt % GlobalKafkaConstant.DEFAULT_PARTITION_COUNT;
	}

	@Override
	public void close() {
		
	}
	
}
