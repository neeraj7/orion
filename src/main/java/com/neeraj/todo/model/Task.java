package com.neeraj.todo.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.neeraj.todo.constant.TaskPriority;
import com.neeraj.todo.constant.TaskStatus;

@Document
public class Task {
	
	@Id
	private String id;
	private String title;
	private String desc;
	@CreatedDate
	private Date created;
	private TaskStatus status;
	private TaskPriority priority;
	private String category;
	
	public Task() {
		super();
	}
	
	public String getId() {
		return id;
	}
	
	public Task(String title, String desc, Date created, TaskStatus status,
			TaskPriority priority, String category) {
		super();
		this.title = title;
		this.desc = desc;
		this.created = created;
		this.status = status;
		this.priority = priority;
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getCreatedDate() {
		return created;
	}
	public void setCreatedDate(Date date) {
		this.created = date;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public TaskPriority getPriority() {
		return priority;
	}
	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
