package com.acme.kafka.annotation.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.acme.kafka.annotation.config.constant.AnnotationKafkaConstant;
import com.acme.kafka.annotation.consumer.ReveiveToKafka;
import com.acme.kafka.annotation.util.AnnotationKafkaUtil;

@Service
public class KafkaServiceImpl extends AbstractKafkaService implements KafkaService {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaServiceImpl.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	ApplicationContext appContext;
	
	@Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
	
	@Override
	public void initKafkaConsumers() throws Exception {
		// Registramos métodos con anotación en los Managed beans de Spring 
     	for(String beanName:appContext.getBeanDefinitionNames()) {
     
     		Object obj = appContext.getBean(beanName);
     		Class<?> objClazz = obj.getClass();
     		
            if(AopUtils.isAopProxy(obj)) {
                objClazz = AopUtils.getTargetClass(obj);
            }
            
            for(Method method:objClazz.getDeclaredMethods()) {
            	
                if(method.isAnnotationPresent(ReveiveToKafka.class)) {
                	method.setAccessible(true);
                	ReveiveToKafka annot = method.getAnnotation(ReveiveToKafka.class);
                	Object objInstance = appContext.getBean(objClazz);
                	
            		String[] topicsResolve = AnnotationKafkaUtil.INSTANCE.parameterTopicConverter(env, annot.topics());
            				
                	for(String topic:topicsResolve) {
                		LOG.debug("Registering Kafka Listener: [topic: '{}', objInstance: '{}', method: '{}']",topic,objInstance,method.getName());
                		addConsumerTopicContext(topic,objInstance,method);
                	}
                	
                }
            }
     	}
     	// Guardamos la lista de 'topics' definidos en todas las anotaciones
     	ConfigurableEnvironment environment = (ConfigurableEnvironment) appContext.getEnvironment();
     	
     	Properties props = new Properties();
     	props.put(AnnotationKafkaConstant.FWK_PROP_TOPICS_ID,String.join(",",getAllTopics()));
     	environment.getPropertySources().addFirst(new PropertiesPropertySource(AnnotationKafkaConstant.FWK_PROP_GROUP_ID,props));
	}
	
	@Override
	public void sendToTopic(String topic,Object message) {
		LOG.info("Send to Kafka topic '{}'. Message = '{}';",topic,message);
		kafkaTemplate.send(topic,message);
	}

	@Override
	@KafkaListener(topics = "#{'${"+AnnotationKafkaConstant.FWK_PROP_TOPICS_ID+"}'.split(',')}")
	@SuppressWarnings("rawtypes")
	public void genericKafkaListener(ConsumerRecord consumerRecord) throws Exception {
		String topic = consumerRecord.topic();
		try {
			List<TopicConsumerContext> topicConsCtxList = getConsumerTopicContext(topic);
			for(TopicConsumerContext topicConsCtx:topicConsCtxList) {
				topicConsCtx.getMethod().invoke(topicConsCtx.getObjInstance(),consumerRecord.value());
			}
		}
		catch(Exception ex) {
			throw new RuntimeException("Severe error invoking the method on topic: '"+topic+"'");
		}
	}
	
}