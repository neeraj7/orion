package com.neeraj.todo.handlers;

import static com.neeraj.todo.constant.ApplicationConstants.TASK_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.neeraj.todo.exceptions.ToDoException;
import com.neeraj.todo.model.SuccessResponse;
import com.neeraj.todo.model.Task;
import com.neeraj.todo.model.ToDoError;
import com.neeraj.todo.service.TaskService;
import com.neeraj.todo.validator.TaskValidator;

import reactor.core.publisher.Mono;

/**
 * Handler class which define methods to handle the user request.
 * 
 * @author neeraj.kumar
 *
 */
@Component
public class TaskHandler {

	/**
	 * TaskService instance.
	 */
	@Autowired
	private TaskService taskService;

	/**
	 * To-do vaidator
	 */
	@Autowired
	private TaskValidator taskValidator;

	/**
	 * Method search for the to-do task in the database. A to-do 'task_id' is
	 * passed as path argument in the request.
	 * 
	 * @param request
	 * @return if found, return it in the Mono<ServerResponse>. else, return not
	 *         found in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> getTaskById(ServerRequest request) {
		// Find the task by id in the db
		return taskService.getTaskById(request.pathVariable(TASK_ID))
				// If found, then prepare the success response
				.flatMap(task -> prepareSuccessResponse(task, HttpStatus.OK))
				// If not found, then prepare the empty response
				.switchIfEmpty(notFoundResponse())
				// If any error occurs, then prepare the error response
				.onErrorResume(e -> prepareErrorResponse(e));
	}

	/**
	 * Method returns all the available to-do tasks in the database.
	 * 
	 * @param request
	 * @return Flux<ItemOnList> in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> getTasks(ServerRequest request) {
		return taskService.getTasks()
				.collectList()
				.flatMap(response -> prepareSuccessResponse(response, HttpStatus.OK))
				.onErrorResume(e -> prepareErrorResponse(e));
	}

	/**
	 * Add a new to-do task in the database.
	 * 
	 * @param request
	 * @return added to-do task in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> createTask(ServerRequest request) {
		// Validate the request body
		return taskValidator.validateBody(Task.class, request)
					// Call to createTask service method
					.flatMap(task -> taskService.createTask(task))
					// If successfully created then prepare the success response
					.flatMap(response -> prepareSuccessResponse(response, HttpStatus.CREATED))
					// If unsuccessful, then prepare the error response
					.onErrorResume(e -> prepareErrorResponse(e));
	}

	/**
	 * Update an existing to-do task in the database.
	 * 
	 * @param request
	 * @return updated to-do task in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> updateTask(ServerRequest request) {
		// Validate the request body
		return taskValidator.validateBody(Task.class, request)
					// Call to updateTask service method
					.flatMap(task -> taskService.updateTask(request.pathVariable(TASK_ID), task))
					// If successfully created then prepare the success response
					.flatMap(response -> prepareSuccessResponse(response, HttpStatus.OK))
					// If unsuccessful, then prepare the error response
					.onErrorResume(e -> prepareErrorResponse(e));
	}

	/**
	 * Delete all tasks in the database.
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> deleteTasks(ServerRequest request) {
		return taskService.deleteTasks()
				.then(emptyResponse())
				.onErrorResume(e -> prepareErrorResponse(e));
	}

	/**
	 * Delete an existing to-do task from the database.
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> deleteTaskById(ServerRequest request) {
		return taskService.deleteTaskById(request.pathVariable(TASK_ID))
				.then(emptyResponse())
				.onErrorResume(e -> prepareErrorResponse(e));
	}

	/**
	 * Prepares the empty response.
	 * 
	 * @return ServerResponse
	 */
	private Mono<ServerResponse> emptyResponse() {
		return ServerResponse.noContent().build();
	}

	/**
	 * Prepares the success response.
	 * 
	 * @param Task, task
	 * 		  HttpStatus, response status
	 * @return ServerResponse
	 */
	private Mono<ServerResponse> prepareSuccessResponse(Task task, HttpStatus status) {
		return ServerResponse.status(status).contentType(APPLICATION_JSON)
				.body(BodyInserters.fromObject(task));
	}
	
	/**
	 * Prepares the success response.
	 * 
	 * @param List<Task>, list of tasks
	 * 		  HttpStatus, response status
	 * @return ServerResponse
	 */
	private Mono<ServerResponse> prepareSuccessResponse(List<Task> task, HttpStatus status) {
		return ServerResponse.status(status).contentType(APPLICATION_JSON)
				.body(BodyInserters.fromObject(task));
	}
	
	/**
	 * Prepares the not found response.
	 */
	private Mono<ServerResponse> notFoundResponse() {
		ToDoError error = new ToDoError(HttpStatus.NOT_FOUND.value(), "Not Found",
				"No task is found.");
		return ServerResponse.status(HttpStatus.NOT_FOUND).contentType(APPLICATION_JSON)
				.body(BodyInserters.fromObject(error));
	}
	
	/**
	 * Prepares the error response.
	 */
	private Mono<ServerResponse> prepareErrorResponse(Throwable e) {
		ToDoException er = (ToDoException) e;
		return ServerResponse.status(er.getExceptions().get(0).getErrorCode()).body(
				Mono.just(er.getExceptions().get(0)), ToDoError.class);
	}
}
