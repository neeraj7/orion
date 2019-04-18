package com.neeraj.todo.router;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.neeraj.todo.constant.ApplicationConstants;
import com.neeraj.todo.handlers.TaskHandler;

/**
 * Mapping the REST endpoints with the appropriate handlers.
 * 
 * @author neeraj.kumar
 *
 */
@Configuration
public class TaskRouter {

	
	@Bean
	public RouterFunction<ServerResponse> defaultRoutes(TaskHandler taskHandler) {
		// Adding the prefix to the endpoints
		return nest(path(ApplicationConstants.TASKS_V1),  
				// Every endpoints response will be in json format
				nest(accept(APPLICATION_JSON), 			 
						route(GET(""), taskHandler::getTasks)
						.andRoute(GET("/{task_id}"), taskHandler::getTaskById)
						// Content type in the PUT and POST request must be in json format
						.andNest(contentType(APPLICATION_JSON),  
								route(POST("").and(contentType(APPLICATION_JSON)), taskHandler::createTask)
					            .andRoute(PUT("/{task_id}").and(contentType(APPLICATION_JSON)), taskHandler::updateTask))
						.andRoute(DELETE(""), taskHandler::deleteTasks)
			            .andRoute(DELETE("/{task_id}"), taskHandler::deleteTaskById)));
	}
	
}
