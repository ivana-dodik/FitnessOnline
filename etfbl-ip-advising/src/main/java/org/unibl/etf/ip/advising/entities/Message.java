package org.unibl.etf.ip.advising.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

	private int id;
	private int userId;
	private String content;
	private Boolean isRead;
	private String dateTime;

	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public Message(int id, int userId, String content, boolean isRead, String dateTime) {
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.isRead = isRead;
		this.dateTime = dateTime;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Boolean isRead() {
		return isRead;
	}


	public void setRead(Boolean isRead) {
		this.isRead = isRead;
	}


	public String getDateTime() {
		return dateTime;
	}


	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public String getDateTimeCustomFormat() {
		Timestamp timestamp = Timestamp.valueOf(dateTime);
        LocalDateTime dt = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return dt.format(formatter) + "h";

	}

}
