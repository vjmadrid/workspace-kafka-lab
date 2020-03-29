package com.acme.connector.kafka.spring.rest.producer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.acme.connector.kafka.spring.rest.producer.constant.EventConstant;
import com.acme.connector.kafka.spring.rest.producer.service.EventService;

@RestController
@RequestMapping(EventConstant.MAPPING_EVENT)
public class EventController {

	public static final Logger LOG = LoggerFactory.getLogger(EventController.class);

	@Autowired
	private EventService eventService;
	
	@GetMapping("/")
	public void isAlived() {
		LOG.info("[EventController] is Alived ...");
	}
	
	@PostMapping
	public ResponseEntity<?> sendEvent(@RequestBody String message, UriComponentsBuilder ucBuilder,
			HttpServletRequest request) {
		LOG.info("[EventController] sending event with message : {}", message);
		
		String eventResult = eventService.sendEvent(message);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path(EventConstant.MAPPING_EVENT).buildAndExpand(message).toUri());
		
		return new ResponseEntity<String>(eventResult, HttpStatus.CREATED);
	}
	
	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

}