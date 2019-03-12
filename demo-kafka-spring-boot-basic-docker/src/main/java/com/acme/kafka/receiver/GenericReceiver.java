package com.acme.kafka.receiver;

import java.util.stream.StreamSupport;

import org.apache.kafka.common.header.Headers;

public class GenericReceiver {

	protected static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
	
}
