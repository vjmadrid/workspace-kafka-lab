package com.acme.connector.kafka.spring.rest.producer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.acme.connector.kafka.spring.rest.producer.constant.MessageConstant;
import com.acme.connector.kafka.spring.rest.producer.service.MessageService;

@RestController
@RequestMapping(MessageConstant.MAPPING_MESSAGE)
public class MessageController {

	public static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private MessageService messageService;
	
	@GetMapping("/")
	public void isAlived() {
		LOG.info("[MessageController] is Alived ...");
	}

	@PostMapping
	public ResponseEntity<?> sendMessage(@RequestBody String message, UriComponentsBuilder ucBuilder,
			HttpServletRequest request) {
		LOG.info("[MessageController] sending message : {}", message);
		
		final String result = messageService.send(message);
		final String messageResult = messageSource.getMessage(MessageConstant.MESSAGE_SENDED, new Object[] { result }, RequestContextUtils.getLocale(request));

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path(MessageConstant.MAPPING_MESSAGE).buildAndExpand(message).toUri());
		
		return new ResponseEntity<String>(messageResult, HttpStatus.CREATED);
	}
	
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}