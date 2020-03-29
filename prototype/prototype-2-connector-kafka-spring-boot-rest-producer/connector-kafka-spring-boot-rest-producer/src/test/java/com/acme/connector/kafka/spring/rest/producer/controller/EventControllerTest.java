package com.acme.connector.kafka.spring.rest.producer.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.acme.connector.kafka.spring.rest.producer.controller.EventController;
import com.acme.connector.kafka.spring.rest.producer.service.EventService;

public class EventControllerTest {
	
	private EventController eventController;

	private EventService eventService;
	
	private String messageTest;
	
	private HttpServletRequest request;
	
	private UriComponentsBuilder uriComponentsBuilder;
	
	@Before
	public final void setUp() throws Exception {
		messageTest = "TEST";

		eventController = spy(new EventController());
		eventService = mock(EventService.class);
		
		request = mock(HttpServletRequest.class);
		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		
		eventController.setEventService(eventService);
		
		when(eventService.sendEvent(anyString())).thenReturn(messageTest);
	}


	@Test
	public final void shouldSendEvent() {
		final ResponseEntity<String> responseEntity = (ResponseEntity<String>) eventController.sendEvent(messageTest,uriComponentsBuilder,request);
		
		assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
		assertNotNull(responseEntity.getHeaders());
	}
	
}
