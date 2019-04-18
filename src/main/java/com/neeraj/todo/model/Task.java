package com.neeraj.todo.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.neeraj.todo.constant.TaskPriority;
import com.neeraj.todo.constant.TaskStatus;

@Document
public class Task {
	
	private String title;
	private String desc;
	private Date date;
	private TaskStatus status;
	private TaskPriority priority;
	private String category;
	
	public Task() {
		super();
	}
	
	public Task(String title, String desc, Date date, TaskStatus status,
			TaskPriority priority, String category) {
		super();
		this.title = title;
		this.desc = desc;
		this.date = date;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
