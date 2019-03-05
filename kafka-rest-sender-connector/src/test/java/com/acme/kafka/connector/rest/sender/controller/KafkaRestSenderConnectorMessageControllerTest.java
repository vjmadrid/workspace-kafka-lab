package com.acme.kafka.connector.rest.sender.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.acme.kafka.connector.rest.sender.service.KafkaRestSenderConnectorMessageService;

public class KafkaRestSenderConnectorMessageControllerTest {
	
	private final String MESSAGE_SOURCE_VALUE = "Error CustomMessage TEST";
	
	private KafkaRestSenderConnectorMessageController kafkaRestSenderConnectorMessageController;

	private KafkaRestSenderConnectorMessageService kafkaRestSenderConnectorMessageService;
	
	private String messageTest;
	
	private HttpServletRequest request;
	
	private MessageSource messageSource;
	
	private UriComponentsBuilder uriComponentsBuilder;
	
	@Before
	public final void setUp() throws Exception {
		messageTest = "TEST";

		kafkaRestSenderConnectorMessageController = spy(new KafkaRestSenderConnectorMessageController());
		kafkaRestSenderConnectorMessageService = mock(KafkaRestSenderConnectorMessageService.class);
		
		request = mock(HttpServletRequest.class);
		messageSource = mock(MessageSource.class);
		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		
		kafkaRestSenderConnectorMessageController.setKafkaRestSenderConnectorMessageService(kafkaRestSenderConnectorMessageService);
		
		when(kafkaRestSenderConnectorMessageService.send(anyString())).thenReturn(messageTest);
		
		when(messageSource.getMessage(anyString(), any(Object[].class), anyObject())).thenReturn(MESSAGE_SOURCE_VALUE);
		
		kafkaRestSenderConnectorMessageController.setMessageSource(messageSource);
	}


	@Test
	public final void shouldSendMessage() {
		final ResponseEntity<String> responseEntity = (ResponseEntity<String>) kafkaRestSenderConnectorMessageController.sendMessage(messageTest,uriComponentsBuilder,request);
		
		assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
		assertNotNull(responseEntity.getHeaders());
	}
	
}
