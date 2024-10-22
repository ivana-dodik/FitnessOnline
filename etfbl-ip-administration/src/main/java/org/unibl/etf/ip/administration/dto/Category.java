package org.unibl.etf.ip.administration.dto;

import java.util.ArrayList;
import java.util.Objects;

public class Category {
	private int id;
	private String name;
	private int parentId;
	private boolean deleted;
	private ArrayList<Attribute> attributes;
	
	public Category(int id, int parentId, String name, boolean deleted, ArrayList<Attribute> attributes) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.deleted = deleted;
		this.attributes = attributes;
	}
	
	public Category(int categoryId, String categoryName, int parentId) {
		this.id = categoryId;
		this.name = categoryName;
		this.parentId = parentId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(name, other.name);
	}

	public Category(String name, int parentId) {
		this.name = name;
		this.parentId = parentId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
