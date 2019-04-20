package com.neeraj.todo.service;

import com.neeraj.todo.model.Task;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A service layer between controller and repository.
 * 
 * @author neeraj.kumar
 *
 */
public interface TaskService {

	/**
	 * Get all the tasks from the database.
	 * 
	 * @return Flux<Task> 
	 */
	public Flux<Task> getTasks();
	
	/**
	 * Create a to-do task in the database.
	 * 
	 * @param task
	 */
	public Mono<Task> createTask(Task task);
	
	/**
	 * Update to-do task in the database.
	 * 
	 * @param task
	 */
	public Mono<Task> updateTask(String id, Task task);

	/**
	 * Get the to-do task by task_id.
	 * 
	 * @param task_id
	 * @return Mono<Task>
	 */
	public Mono<Task> getTaskById(String id);
	
	/**
	 * Delete the to-do task by task_id.
	 * 
	 * @param task_id
	 * @return Mono<Void>
	 */
	public Mono<Void> deleteTaskById(String id);
	
	/**
	 * Delete all tasks.
	 * @return Mono<Void>
	 */
	public Mono<Void> deleteTasks();
}
