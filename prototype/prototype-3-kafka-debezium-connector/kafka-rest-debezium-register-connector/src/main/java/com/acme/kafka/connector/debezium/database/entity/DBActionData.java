package com.acme.kafka.connector.debezium.database.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DBActionData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String op;
	
	private Long tsMs;
	
	private DBActionSourceData source;
	
	private String before;
	
	private String after;

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public Long getTsMs() {
		return tsMs;
	}

	public void setTsMs(Long tsMs) {
		this.tsMs = tsMs;
	}

	public DBActionSourceData getSource() {
		return source;
	}

	public void setSource(DBActionSourceData source) {
		this.source = source;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	@SuppressWarnings("unchecked")
	@JsonProperty("payload")
	private void unpackNested(Map<String,Object> payload) throws IOException {
		if(payload != null) {
			ObjectMapper mapper = new ObjectMapper();
			this.op = (String) payload.get("op");
			this.tsMs = (Long) payload.get("ts_ms");
			this.source = mapper.readValue(mapper.writeValueAsString(payload.get("source")), DBActionSourceData.class);
			LinkedHashMap<String,Object> before = (LinkedHashMap<String,Object>) payload.get("before");
			if(before != null) {
				this.before = mapper.writeValueAsString(before);
			}
			LinkedHashMap<String,Object> after = (LinkedHashMap<String,Object>) payload.get("after");
			if(after != null) {
				this.after = mapper.writeValueAsString(after);
			}
		}	
	}
	
	@Override
	public String toString() {
		return "DBActionData [op=" + op + ", tsMs=" + tsMs + ", source=" + source + ", before=" + before + ", after=" + after + "]";
	}
	
}