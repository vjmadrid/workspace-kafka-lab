package com.acme.kafka.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomMessage {

	private int id;

	private String message;

	public CustomMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomMessage(@JsonProperty("id") final int id, @JsonProperty("message") final String message) {
		this.id = id;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CustomMessage::toString() {" + "id='" + id + '\'' + ", message=" + message + '}';
	}
	
	public String toJson() {
		return "{" + "id='" + id + '\'' + ", message=" + message + '}';
	}

}
