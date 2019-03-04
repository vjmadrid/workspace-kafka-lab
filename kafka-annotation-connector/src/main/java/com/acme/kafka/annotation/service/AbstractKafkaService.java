package com.acme.kafka.annotation.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

public abstract class AbstractKafkaService {

	public class TopicConsumerContext {

		private Object objInstance;
		
		private Method method;
		
		public TopicConsumerContext(Object objInstance,Method method) {
			this.objInstance = objInstance;
			this.method = method;
		}

		public Object getObjInstance() {
			return objInstance;
		}

		public void setObjInstance(Class<?> objInstance) {
			this.objInstance = objInstance;
		}

		public Method getMethod() {
			return method;
		}

		public void setMethod(Method method) {
			this.method = method;
		}
		
	}  

	private Map<String,List<TopicConsumerContext>> topicConsCtxMap = new HashMap<String,List<TopicConsumerContext>>();
	
	@PostConstruct
	public void init() throws Exception {
		initKafkaConsumers();
	}
	
	public abstract void initKafkaConsumers() throws Exception;

	protected void addConsumerTopicContext(String topic,Object objInstance,Method method) {
		if(!this.topicConsCtxMap.containsKey(topic)) {
			this.topicConsCtxMap.put(topic,new ArrayList<TopicConsumerContext>());
		}
		this.topicConsCtxMap.get(topic).add(new TopicConsumerContext(objInstance,method));
	}
	
	protected List<String> getAllTopics() {
		return new ArrayList<String>(this.topicConsCtxMap.keySet());
	}
	
	protected List<TopicConsumerContext> getConsumerTopicContext(String topic) throws Exception {
		if(!this.topicConsCtxMap.containsKey(topic)) {
			throw new RuntimeException("Topic not found: '"+topic+"'");
		}
		return this.topicConsCtxMap.get(topic);
	}

}