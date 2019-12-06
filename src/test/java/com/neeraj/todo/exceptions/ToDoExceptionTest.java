package com.neeraj.todo.exceptions;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.neeraj.todo.model.ToDoError;

/**
 * ToDoExceptionTest class.
 * 
 * @author Neeraj
 *
 */
public class ToDoExceptionTest {

	/**
	 * Test the single arg constructor.
	 */
	@Test
	public void testToDoException() {
		ToDoError error = new ToDoError(500, "error", "description");
		ToDoException tde = new ToDoException(error);
		List<ToDoError> errorList = tde.getExceptions();
		assertTrue(errorList.size() == 1);
	}
	
	/**
	 * Test the constructor with List arg type.
	 */
	@Test
	public void testToDoExceptionWithListArgs() {
		ToDoError error1 = new ToDoError(500, "error", "description");
		ToDoError error2 = new ToDoError(500, "error", "description");
		List<ToDoError> errorList = new ArrayList<>();
		errorList.add(error1);
		errorList.add(error2);		
		ToDoException tde = new ToDoException(errorList);
		List<ToDoError> errorListResponse = tde.getExceptions();
		assertTrue(errorListResponse.size() == 2);
	}
	
}
