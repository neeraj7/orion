package com.neeraj.todo.handlers;

import java.time.Instant;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.neeraj.todo.constant.TaskPriority;
import com.neeraj.todo.constant.TaskStatus;
import com.neeraj.todo.model.Task;
import com.neeraj.todo.service.TaskService;
import com.neeraj.todo.validator.TaskValidator;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * TaskHandlerTest class.
 * 
 * @author neeraj.kumar
 *
 */
@RunWith(SpringRunner.class)
public class TaskHandlerTest {
	
	@InjectMocks
	private TaskHandler taskHandler;
	
	@Mock
	private TaskService mockTaskService;
	
	@Mock
	private TaskValidator mockTaskValidator;
	
	@Mock
	private ServerRequest mockRequest;
	
	@Test
	public void testGetTaskById() {
		Task task = new Task("title", "desc", Date.from(Instant.now()), TaskStatus.NOT_COMPLETED, TaskPriority.MEDIUM, "category");
		Mockito.when(mockTaskService.getTaskById(Mockito.any())).thenReturn(Mono.just(task));
		Mono<ServerResponse> response = taskHandler.getTaskById(mockRequest);
		StepVerifier.create(response).expectNextMatches(res -> {
			return res.statusCode().is2xxSuccessful();
		}).verifyComplete();
	}
}
