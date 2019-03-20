package com.neeraj.todo.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.neeraj.todo.model.ToDoError;

/**
 * ToDoException class.
 * 
 * @author Neeraj
 *
 */
public class ToDoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * List of errors
	 */
	private List<ToDoError> errors = new ArrayList<>();
	
	public ToDoException(List<ToDoError> errors) {
		this.errors = errors;
	}
	
	public List<ToDoError> getExceptions() {
		return errors;
	}
	
}
