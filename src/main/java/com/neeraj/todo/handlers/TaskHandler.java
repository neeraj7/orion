package com.neeraj.todo.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.neeraj.todo.model.ItemOnList;
import com.neeraj.todo.repository.ListRepository;

import reactor.core.publisher.Mono;

@Component
public class TaskHandler {

	private final ListRepository listRepo;
	
	public TaskHandler(ListRepository listRepo) {
		this.listRepo = listRepo;
	}
	
	public Mono<ServerResponse> get(ServerRequest request) {
		final Mono<ItemOnList> item = listRepo.findById(request.pathVariable("id"));
		return item.flatMap(p -> ServerResponse.ok()
											   .contentType(MediaType.APPLICATION_JSON)
											   .body(BodyInserters.fromPublisher(item, ItemOnList.class)))
				   .switchIfEmpty(ServerResponse.notFound()
						                        .build());		
	}
	
	public Mono<ServerResponse> all(ServerRequest request) {
		return ServerResponse.ok()
							 .contentType(MediaType.APPLICATION_JSON)
							 .body(BodyInserters.fromPublisher(listRepo.findAll(), ItemOnList.class));
	}
}
