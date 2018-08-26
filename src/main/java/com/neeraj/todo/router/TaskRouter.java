package com.neeraj.todo.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.neeraj.todo.handlers.TaskHandler;

/**
 * 
 * @author neeraj.kumar
 *
 */
@Configuration
public class TaskRouter {

	
	@Bean
	public RouterFunction<ServerResponse> route(TaskHandler taskHandler) {
		return RouterFunctions.route(GET("/item/{id}").and(accept(APPLICATION_JSON)), taskHandler::get)
	            .andRoute(GET("/items").and(accept(APPLICATION_JSON)), taskHandler::all);	            
	}
	
}
