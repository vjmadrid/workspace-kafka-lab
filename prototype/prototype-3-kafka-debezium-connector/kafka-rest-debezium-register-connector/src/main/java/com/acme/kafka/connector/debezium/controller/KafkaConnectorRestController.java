package com.acme.kafka.connector.debezium.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.acme.kafka.connector.debezium.constant.KafkaRestControllerConstant;
import com.acme.kafka.connector.debezium.kafka.converter.KafkaConnectorConverter;
import com.acme.kafka.connector.debezium.kafka.entity.KafkaConnector;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(KafkaRestControllerConstant.MAPPING)
@Api(value=KafkaRestControllerConstant.API_MESSAGE_VALUE)
public class KafkaConnectorRestController {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaConnectorRestController.class);
	
	@Value("${kafka.connector.url}")
	private String kafkaConnectorURL;
	
	@Autowired
	@Qualifier("customFileKafkaConnector")
	private KafkaConnector customFileKafkaConnector;
		
	@GetMapping("/")
	public void isAlived() {
		LOG.info("[KafkaConnectorRestController] is Alived ...");
	}
	
	private String registerWithKafkaConnect(String jsonData)  throws JsonProcessingException  {
		LOG.info("[KafkaConnectorRestController] Register with Kafka Connect: {}: {}",kafkaConnectorURL,jsonData);
		
		String jsonResult = null;
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(jsonData,headers);
		ResponseEntity<String> response = null;
		
		try {
			response = restTemplate.postForEntity(this.kafkaConnectorURL,entity,String.class);
			jsonResult = response.getBody(); 
		}
		catch(Exception ex) {
			jsonResult = "Error: "+ex.getMessage()+". Connector already registered?";
		}
		return jsonResult;
	}
	
	@PostMapping("/register")
	@ApiOperation(value="Register the Kafka Connector for mysql with POST Request")
	public String registerPostRequest(@Valid @RequestBody String jsonDataBody) throws JsonProcessingException {
		LOG.info("[KafkaConnectorRestController] Register Connector with POST Request : {}", jsonDataBody);
		
		if ((jsonDataBody != null) && (!"".contentEquals(jsonDataBody))) {
			return registerWithKafkaConnect(jsonDataBody);
		}
		
		return null;
	}
	
	@RequestMapping(value="/register-default-file", method=RequestMethod.GET, produces=MediaType.TEXT_PLAIN_VALUE)
	@ApiOperation(value="Register the Kafka Connector for mysql with file")
	public String registerDefaultFile(HttpServletResponse res) throws JsonProcessingException{
		LOG.info("[KafkaConnectorRestController] Register Connector With File : {}", customFileKafkaConnector.getName());
		String jsonDataFile = KafkaConnectorConverter.INSTANCE.fromKafkaConnectorToJSON(customFileKafkaConnector, false); 
		
		if ((jsonDataFile != null) && (!"".contentEquals(jsonDataFile))) {
			return registerWithKafkaConnect(jsonDataFile);
		}

		return null;
    }
	
	
	
}