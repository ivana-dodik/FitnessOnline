package org.unibl.etf.ip.advising.beans;

import java.io.Serializable;


public class AdvisorBean implements Serializable {

	private static final long serialVersionUID = 5129132478994887140L;

	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean loggedIn;
	
	
	public AdvisorBean() {
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isLoggedIn() {
		return loggedIn;
	}


	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void logOut() {
		this.loggedIn = false;
	}
	
	public void copyAdvisorBean(AdvisorBean ab) {
		this.firstName = ab.firstName;
		this.lastName = ab.lastName;
		this.username = ab.username;
		this.loggedIn = ab.loggedIn;
	}

}
