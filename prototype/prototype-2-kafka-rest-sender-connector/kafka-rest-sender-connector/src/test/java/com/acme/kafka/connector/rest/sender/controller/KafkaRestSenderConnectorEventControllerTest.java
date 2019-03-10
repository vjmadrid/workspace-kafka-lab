package com.acme.kafka.connector.rest.sender.controller;

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

import com.acme.kafka.connector.rest.sender.service.KafkaRestSenderConnectorEventService;

public class KafkaRestSenderConnectorEventControllerTest {
	
	private KafkaRestSenderConnectorEventController kafkaRestSenderConnectorEventController;

	private KafkaRestSenderConnectorEventService kafkaRestSenderConnectorEventService;
	
	private String messageTest;
	
	private HttpServletRequest request;
	
	private UriComponentsBuilder uriComponentsBuilder;
	
	@Before
	public final void setUp() throws Exception {
		messageTest = "TEST";

		kafkaRestSenderConnectorEventController = spy(new KafkaRestSenderConnectorEventController());
		kafkaRestSenderConnectorEventService = mock(KafkaRestSenderConnectorEventService.class);
		
		request = mock(HttpServletRequest.class);
		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		
		kafkaRestSenderConnectorEventController.setKafkaRestSenderConnectorEventService(kafkaRestSenderConnectorEventService);
		
		when(kafkaRestSenderConnectorEventService.sendEvent(anyString())).thenReturn(messageTest);
	}


	@Test
	public final void shouldSendEvent() {
		final ResponseEntity<String> responseEntity = (ResponseEntity<String>) kafkaRestSenderConnectorEventController.sendEvent(messageTest,uriComponentsBuilder,request);
		
		assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
		assertNotNull(responseEntity.getHeaders());
	}
	
}
