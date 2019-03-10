package com.acme.kafka.connector.rest.sender.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.acme.kafka.connector.rest.sender.constant.KafkaRestSenderConnectorMessageConstant;
import com.acme.kafka.connector.rest.sender.service.KafkaRestSenderConnectorMessageService;

@RestController
@RequestMapping(KafkaRestSenderConnectorMessageConstant.MAPPING_MESSAGE)
public class KafkaRestSenderConnectorMessageController {

	public static final Logger LOG = LoggerFactory.getLogger(KafkaRestSenderConnectorMessageController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private KafkaRestSenderConnectorMessageService kafkaRestSenderConnectorMessageService;
	
	@GetMapping("/")
	public void isAlived() {
		LOG.info("[KafkaRestSenderConnectorMessageController] is Alived ...");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> sendMessage(@RequestBody String message, UriComponentsBuilder ucBuilder,
			HttpServletRequest request) {
		LOG.info("[KafkaRestSenderController] sending message : {}", message);
		
		final String result = kafkaRestSenderConnectorMessageService.send(message);
		final String messageResult = messageSource.getMessage(KafkaRestSenderConnectorMessageConstant.MESSAGE_SENDED, new Object[] { result }, RequestContextUtils.getLocale(request));

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path(KafkaRestSenderConnectorMessageConstant.MAPPING_MESSAGE).buildAndExpand(message).toUri());
		
		return new ResponseEntity<String>(messageResult, HttpStatus.CREATED);
	}
	
	public void setKafkaRestSenderConnectorMessageService(KafkaRestSenderConnectorMessageService kafkaRestSenderConnectorMessageService) {
		this.kafkaRestSenderConnectorMessageService = kafkaRestSenderConnectorMessageService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}