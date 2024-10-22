package org.unibl.etf.ip.administration.dto;

public class Log {
	private int id;
	private String dateTime;
	private String content;
	
	public Log(int id, String dateTime, String content) {
		this.id = id;
		this.dateTime = dateTime;
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
