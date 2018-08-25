package com.neeraj.todo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ItemOnList {
	
	@Id
	private Long id;
	private String name;
	private boolean isComplete;
	
	public ItemOnList(String name) {
		this.name = name;
		this.isComplete = false;
	}

	public ItemOnList() {
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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
