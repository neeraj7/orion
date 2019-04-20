package com.neeraj.todo.model;

public class SuccessResponse {
	
	private String status;
	private String desc;
	
	public SuccessResponse() {}
	
	public SuccessResponse(String status, String desc) {
		super();
		this.status = status;
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
