package org.unibl.etf.ip.advising.beans;

import java.io.Serializable;
import java.sql.Date;

public class MessageBean implements Serializable {

	private static final long serialVersionUID = -8634657816056372931L;
	private int userId;
	private String content;
	private Boolean isRead;
	private Date dateTime;

	public MessageBean() {
		// TODO Auto-generated constructor stub
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

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	
}