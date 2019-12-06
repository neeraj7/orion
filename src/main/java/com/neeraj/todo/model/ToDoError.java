package com.neeraj.todo.model;

/**
 * Error class.
 * 
 * @author Neeraj
 *
 */
public class ToDoError {

	private int errorCode;
	private String errorName;
	private String errorMessage;

	public String getErrorName() {
		return errorName;
	}
	
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public ToDoError(String errorName, String errorMessage) {
		super();
		this.errorName = errorName;
		this.errorMessage = errorMessage;
	}
	
	public ToDoError(int code, String errorName, String errorMessage) {
		super();
		this.errorCode = code;
		this.errorName = errorName;
		this.errorMessage = errorMessage;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public ToDoError() {}

}
