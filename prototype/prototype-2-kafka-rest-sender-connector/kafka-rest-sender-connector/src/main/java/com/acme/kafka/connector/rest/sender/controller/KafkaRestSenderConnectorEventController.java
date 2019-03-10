package com.acme.kafka.connector.rest.sender.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.acme.kafka.connector.rest.sender.constant.KafkaRestSenderConnectorEventConstant;
import com.acme.kafka.connector.rest.sender.service.KafkaRestSenderConnectorEventService;

@RestController
@RequestMapping(KafkaRestSenderConnectorEventConstant.MAPPING_EVENT)
public class KafkaRestSenderConnectorEventController {

	public static final Logger LOG = LoggerFactory.getLogger(KafkaRestSenderConnectorEventController.class);

	@Autowired
	private KafkaRestSenderConnectorEventService kafkaRestSenderConnectorEventService;
	
	@GetMapping("/")
	public void isAlived() {
		LOG.info("[KafkaRestSenderConnectorEventController] is Alived ...");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> sendEvent(@RequestBody String message, UriComponentsBuilder ucBuilder,
			HttpServletRequest request) {
		LOG.info("[KafkaRestSenderController] sending event with message : {}", message);
		
		String eventResult = kafkaRestSenderConnectorEventService.sendEvent(message);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path(KafkaRestSenderConnectorEventConstant.MAPPING_EVENT).buildAndExpand(message).toUri());
		
		return new ResponseEntity<String>(eventResult, HttpStatus.CREATED);
	}
	
	public void setKafkaRestSenderConnectorEventService(KafkaRestSenderConnectorEventService kafkaRestSenderConnectorEventService) {
		this.kafkaRestSenderConnectorEventService = kafkaRestSenderConnectorEventService;
	}

}