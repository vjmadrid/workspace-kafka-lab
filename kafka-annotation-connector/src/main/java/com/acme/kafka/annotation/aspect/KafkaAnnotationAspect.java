package com.acme.kafka.annotation.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

import com.acme.kafka.annotation.producer.SendToKafka;
import com.acme.kafka.annotation.service.KafkaService;
import com.acme.kafka.annotation.util.AnnotationKafkaUtil;

@Aspect
@Component
@Configuration
@MessageMapping
public class KafkaAnnotationAspect {

	@Autowired
	private Environment env;
	
	@Autowired
	private KafkaService kafkaService;
	
	@AfterReturning(value = "@annotation(com.acme.kafka.annotation.producer.SendToKafka)",returning = "result")
	public void afterReturning(JoinPoint joinPoint,Object result) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SendToKafka annot = method.getAnnotation(SendToKafka.class);
		String topics = String.join(",",AnnotationKafkaUtil.INSTANCE.parameterTopicConverter(env, annot.topics()));
		kafkaService.sendToTopic(topics,result);
	}
	
}