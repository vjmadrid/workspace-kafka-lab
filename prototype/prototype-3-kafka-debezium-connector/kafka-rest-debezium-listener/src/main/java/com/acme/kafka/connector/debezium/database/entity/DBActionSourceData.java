package com.acme.kafka.connector.debezium.database.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DBActionSourceData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	@JsonProperty("server_id")
	private Long serverId;

	@JsonProperty("ts_sec")
	private Long tsSec;
	
	private String gtid;
	
	private String file;
	
	private Long pos;
	
	private Long row;
	
	private String snapshot;
	
	private Integer thread;
	
	private String db;
	
	private String table;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public Long getTsSec() {
		return tsSec;
	}

	public void setTsSec(Long tsSec) {
		this.tsSec = tsSec;
	}

	public String getGtid() {
		return gtid;
	}

	public void setGtid(String gtid) {
		this.gtid = gtid;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Long getPos() {
		return pos;
	}

	public void setPos(Long pos) {
		this.pos = pos;
	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public Integer getThread() {
		return thread;
	}

	public void setThread(Integer thread) {
		this.thread = thread;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	@Override
	public String toString() {
		return "DBActionSourceData [name=" + name + ", serverId=" + serverId + ", tsSec=" + tsSec + ", gtid=" + gtid
				+ ", file=" + file + ", pos=" + pos + ", row=" + row + ", snapshot=" + snapshot + ", thread=" + thread
				+ ", db=" + db + ", table=" + table + "]";
	}
	
}