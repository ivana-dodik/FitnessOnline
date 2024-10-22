package org.unibl.etf.ip.administration.dto;

public class Attribute {
	private int id;
	private String name;
	private int categoryId;
	private boolean deleted;
	
	
	public Attribute(String name, int categoryId, boolean deleted) {
		super();
		this.name = name;
		this.categoryId = categoryId;
		this.deleted = deleted;
	}

	public Attribute(int id, String name, int categoryId, boolean deleted) {
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.deleted = deleted;
	}

	public Attribute(int attributeId, String attributeName) {
		this.id = attributeId;
		this.name = attributeName;
	}

	public Attribute(String attributeName, int attributeCategoryId) {
		this.name = attributeName;
		this.categoryId = attributeCategoryId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
