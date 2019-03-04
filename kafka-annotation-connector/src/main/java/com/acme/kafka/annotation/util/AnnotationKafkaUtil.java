package com.acme.kafka.annotation.util;

import org.springframework.core.env.Environment;

import com.acme.kafka.annotation.config.constant.AnnotationKafkaConstant;

public enum AnnotationKafkaUtil {
	
	INSTANCE;
	
	public String[] parameterTopicConverter(Environment env,String[] parameterTopics) {
		
		if (parameterTopics!=null) {
			String[] topicsResolve = new String [parameterTopics.length];
			
			for (int i = 0; i < parameterTopics.length; i++) {
				topicsResolve[i] = parameterTopics[i];
				if (parameterTopics[i].matches(AnnotationKafkaConstant.SPRING_BOOT_PATTERN)) {
					String temp = parameterTopics[i].substring(2, parameterTopics[i].length()-1);
					topicsResolve[i] = env.getProperty(temp);
				} 
			}
			
			return topicsResolve;
		}
		return null;
	}

}
