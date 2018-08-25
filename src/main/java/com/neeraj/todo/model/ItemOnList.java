package com.neeraj.todo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ItemOnList {
	
	@Id
	private String id;
	private String name;
	private boolean isComplete;
	
	public ItemOnList(String name) {
		this.name = name;
		this.isComplete = false;
	}

	public ItemOnList() {
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

}
