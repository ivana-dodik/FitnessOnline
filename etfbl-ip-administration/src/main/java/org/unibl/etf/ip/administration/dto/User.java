package org.unibl.etf.ip.administration.dto;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String city;
	private String username;
	private String password;
	private String email;
	private boolean activated;
	private boolean deleted;
	private String avatarURL;
	
	public User(int id, String firstName, String lastName, String city, String username, String email,
			boolean activated, boolean deleted, String avatar_URL) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.username = username;
		this.email = email;
		this.activated = activated;
		this.deleted = deleted;
		this.avatarURL = avatar_URL;
	}
	
	public User(String firstName, String lastName, String city, String username, String password, String email,
			boolean activated, boolean deleted, String avatar_URL) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.username = username;
		this.password = password;
		this.email = email;
		this.activated = activated;
		this.deleted = deleted;
		this.avatarURL = avatar_URL;
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public boolean hasAvatar() {
		return avatarURL != null;
	}
	public String getAvatarURL() {
		return avatarURL;
	}
	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}
    
}
