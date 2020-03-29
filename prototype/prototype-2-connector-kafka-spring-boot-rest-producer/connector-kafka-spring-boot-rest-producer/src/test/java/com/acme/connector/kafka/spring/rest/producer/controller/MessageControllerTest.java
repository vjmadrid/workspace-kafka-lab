package com.acme.connector.kafka.spring.rest.producer.controller;

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

import com.acme.connector.kafka.spring.rest.producer.controller.MessageController;
import com.acme.connector.kafka.spring.rest.producer.service.MessageService;

public class MessageControllerTest {
	
	private final String MESSAGE_SOURCE_VALUE = "Error CustomMessage TEST";
	
	private MessageController messageController;

	private MessageService messageService;
	
	private String messageTest;
	
	private HttpServletRequest request;
	
	private MessageSource messageSource;
	
	private UriComponentsBuilder uriComponentsBuilder;
	
	@Before
	public final void setUp() throws Exception {
		messageTest = "TEST";

		messageController = spy(new MessageController());
		messageService = mock(MessageService.class);
		
		request = mock(HttpServletRequest.class);
		messageSource = mock(MessageSource.class);
		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		
		messageController.setMessageService(messageService);
		
		when(messageService.send(anyString())).thenReturn(messageTest);
		
		when(messageSource.getMessage(anyString(), any(Object[].class), anyObject())).thenReturn(MESSAGE_SOURCE_VALUE);
		
		messageController.setMessageSource(messageSource);
	}


	@Test
	public final void shouldSendMessage() {
		final ResponseEntity<String> responseEntity = (ResponseEntity<String>) messageController.sendMessage(messageTest,uriComponentsBuilder,request);
		
		assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
		assertNotNull(responseEntity.getHeaders());
	}
	
}
