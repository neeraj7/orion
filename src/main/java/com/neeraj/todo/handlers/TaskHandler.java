package com.neeraj.todo.handlers;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.neeraj.todo.model.ItemOnList;
import com.neeraj.todo.repository.ListRepository;

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
	private final ListRepository listRepo;
	
	/**
	 * Constructor.
	 * 
	 * @param listRepo
	 */
	public TaskHandler(ListRepository listRepo) {
		this.listRepo = listRepo;
	}
	
	/**
	 * Method search for the to-do item in the database.
	 * An to-do item 'id' is passed as path argument in the request.
	 * 
	 * @param request
	 * @return if found, return it in the Mono<ServerResponse>.
	 *         else, return not found in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> get(ServerRequest request) {
		final Mono<ItemOnList> item = listRepo.findById(request.pathVariable("id"));
		return item.flatMap(p -> ServerResponse.ok()
											   .contentType(MediaType.APPLICATION_JSON)
											   .body(fromPublisher(item, ItemOnList.class)))
				   .switchIfEmpty(ServerResponse.notFound()
						                        .build());		
	}
	
	/**
	 * Method returns all the available to-do items in the database.
	 * 
	 * @param request
	 * @return Flux<ItemOnList> in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> all(ServerRequest request) {
		return ServerResponse.ok()
							 .contentType(MediaType.APPLICATION_JSON)
							 .body(fromPublisher(listRepo.findAll(), ItemOnList.class));
	}
	
	/**
	 * Add a new to-do item in the database.
	 * 
	 * @param request
	 * @return added to-do item in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> addNewItem(ServerRequest request) {
		final Mono<ItemOnList> item = request.bodyToMono(ItemOnList.class);
		return ServerResponse.ok()
				             .contentType(MediaType.APPLICATION_JSON)
				             .body(fromPublisher(item.map(p -> new ItemOnList(p.getName())).flatMap(listRepo::save), ItemOnList.class));
	}
	
	/**
	 * Update an existing to-do item in the database.
	 * 
	 * @param request
	 * @return updated to-do item in the Mono<ServerResponse>.
	 */
	public Mono<ServerResponse> updateItem(ServerRequest request) {
		final Mono<ItemOnList> item = request.bodyToMono(ItemOnList.class);
		return ServerResponse.ok()
							 .contentType(MediaType.APPLICATION_JSON)
							 .body(fromPublisher(item.flatMap(listRepo::save), ItemOnList.class));
		
	}
}