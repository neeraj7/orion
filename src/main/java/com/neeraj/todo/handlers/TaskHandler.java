package com.neeraj.todo.handlers;

import static com.neeraj.todo.constant.ApplicationConstants.TASK_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

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
		return taskService.getTaskById(request.pathVariable(TASK_ID))
				.flatMap(task -> ServerResponse.ok()
						.contentType(APPLICATION_JSON)
						.body(BodyInserters.fromObject(task)))
				.switchIfEmpty(emptyResponse()).onErrorResume(err -> {
					ToDoError error = new ToDoError("Not Found",
							"No task is found.");
					return ServerResponse.status(HttpStatus.NOT_FOUND)
							.contentType(APPLICATION_JSON)
							.body(BodyInserters.fromObject(error));
				});
	}

	/**
	 * Method returns all the available to-do tasks in the database.
	 * 
	 * @param request
	 * @return Flux<ItemOnList> in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> getTasks(ServerRequest request) {
		return taskService.getTasks().collectList()
				.flatMap(task -> ServerResponse.ok()
						.contentType(APPLICATION_JSON)
						.body(BodyInserters.fromObject(task)))
				.switchIfEmpty(emptyResponse()).onErrorResume(e -> {
					ToDoException er = (ToDoException) e;
					return ServerResponse.badRequest().body(
							Mono.just(er.getExceptions().get(0)),
							ToDoError.class);
				});
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
				.flatMap(body -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						// save the task into the db
						// and return as the saved task in response
						.body(fromPublisher(taskService.createTask(body),
								Task.class))
						.onErrorResume(e -> {
							ToDoException er = (ToDoException) e;
							return ServerResponse.badRequest().body(
									Mono.just(er.getExceptions().get(0)),
									ToDoError.class);
						}));
	}

	/**
	 * Update an existing to-do task in the database.
	 * 
	 * @param request
	 * @return updated to-do task in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> updateTask(ServerRequest request) {
		// Validate the request body
		return taskValidator.validateBody(Task.class, request).flatMap(task -> {
			// Call to updateTask method
			return taskService.updateTask(request.pathVariable(TASK_ID), task)
					// on success
					.flatMap(t -> ServerResponse.status(HttpStatus.CREATED)
							.contentType(APPLICATION_JSON)
							.body(BodyInserters.fromObject(t)))
					// on failure
					.onErrorResume(error -> {
						return ServerResponse.status(HttpStatus.BAD_REQUEST)
								.contentType(APPLICATION_JSON).syncBody(
										"Error happened during updating the task.");
					});
			// When validation fails
		}).onErrorResume(e -> {
			ToDoException er = (ToDoException) e;
			return ServerResponse.badRequest().body(
					Mono.just(er.getExceptions().get(0)), ToDoError.class);
		});
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
				.onErrorResume(e -> {
					ToDoException er = (ToDoException) e;
					return ServerResponse.status(er.getExceptions().get(0).getErrorCode()).body(
							Mono.just(er.getExceptions().get(0)), ToDoError.class);
				});
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
				.onErrorResume(e -> {
					ToDoException er = (ToDoException) e;
					return ServerResponse.status(er.getExceptions().get(0).getErrorCode()).body(
							Mono.just(er.getExceptions().get(0)), ToDoError.class);
				});
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
	 * @param desc, description message
	 * @return ServerResponse
	 */
	private Mono<ServerResponse> successResponse(String desc) {
		return ServerResponse.ok().contentType(APPLICATION_JSON)
				.body(BodyInserters.fromObject(
						new SuccessResponse(HttpStatus.OK.toString(), desc)));
	}
}
