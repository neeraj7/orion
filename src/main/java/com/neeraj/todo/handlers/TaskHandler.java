package com.neeraj.todo.handlers;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.neeraj.todo.exceptions.ToDoException;
import com.neeraj.todo.model.ItemOnList;
import com.neeraj.todo.model.ToDoError;
import com.neeraj.todo.repository.ListRepository;
import com.neeraj.todo.validator.ToDoValidator;

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
	 * ListRepository instance for database operations.
	 */
	@Autowired
	private ListRepository listRepo;

	/**
	 * To-do vaidator
	 */
	@Autowired
	private ToDoValidator toDoValidator;

//	/**
//	 * Constructor.
//	 * 
//	 * @param listRepo
//	 */
//	public TaskHandler(ListRepository listRepo) {
//		this.listRepo = listRepo;
//	}

	/**
	 * Method search for the to-do task in the database. A to-do 'task_id' is
	 * passed as path argument in the request.
	 * 
	 * @param request
	 * @return if found, return it in the Mono<ServerResponse>. else, return not
	 *         found in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> getTaskById(ServerRequest request) {
		final Mono<ItemOnList> item = listRepo
				.findById(request.pathVariable("task_id"));
		return item
				.flatMap(p -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromPublisher(item, ItemOnList.class)))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	/**
	 * Method returns all the available to-do tasks in the database.
	 * 
	 * @param request
	 * @return Flux<ItemOnList> in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> getTasks(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(fromPublisher(listRepo.findAll(), ItemOnList.class));
	}

	/**
	 * Add a new to-do task in the database.
	 * 
	 * @param request
	 * @return added to-do task in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> createTask(ServerRequest request) {
		return toDoValidator.validateBody(ItemOnList.class, request) // Validate the request body
				.flatMap(body -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromPublisher(listRepo.save(body), // save the item into the db and return as the saved item in response
								ItemOnList.class)) 
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
		final Mono<ItemOnList> item = request.bodyToMono(ItemOnList.class);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
				fromPublisher(item.flatMap(listRepo::save), ItemOnList.class));
	}

	/**
	 * Delete all tasks in the database.
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> deleteTasks(ServerRequest request) {
		// TODO: Write code to delete a single task
		  return toDoValidator.validateBody(ItemOnList.class, request)
		 			  .flatMap(item -> {
		 				 listRepo.findById(item.getId())
		 				 		 .flatMap(i -> listRepo.delete(i));
		 				 listRepo.deleteAll();
		 				return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
						.syncBody("Deleted");
		 			  })
		 			  .onErrorResume(error -> {
		 				  System.out.println("Error in deletion - " + error.getMessage());
		 				  return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody("Can't delete. Error Happened");
		 			  });
		 			  
//		 final Mono<ItemOnList> item = request.bodyToMono(ItemOnList.class);
//		final Mono<ItemOnList> item = request
//				.body(BodyExtractors.toMono(ItemOnList.class));
//		item.flatMap(i -> listRepo.deleteById(i.getId()))
//				.doOnSuccess(i -> System.out.println("Successfull "))
//				.doOnError(i -> System.out.println("Error "));
//		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
//				.syncBody("Deleted");
	}
	
	/**
	 * Delete an existing to-do task from the database.
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> deleteTaskById(ServerRequest request) {
		// TODO: Write code to delete a single task
		return null;
	}
}
