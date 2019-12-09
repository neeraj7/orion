package com.neeraj.todo;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.time.Instant;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.neeraj.todo.constant.TaskPriority;
import com.neeraj.todo.constant.TaskStatus;
import com.neeraj.todo.exceptions.ToDoException;
import com.neeraj.todo.handlers.TaskHandler;
import com.neeraj.todo.model.Task;
import com.neeraj.todo.model.ToDoError;
import com.neeraj.todo.repository.ListRepository;
import com.neeraj.todo.router.TaskRouter;
import com.neeraj.todo.service.impl.TaskServiceImpl;
import com.neeraj.todo.validator.TaskValidator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ToDoApplicationTest class.
 * 
 * @author neeraj.kumar
 *
 */
@RunWith(SpringRunner.class)
@WebFluxTest
@AutoConfigureDataMongo
@Import(value = {TaskServiceImpl.class,TaskRouter.class, TaskHandler.class, TaskValidator.class})
public class ToDoApplicationTest {

	/**
	 * WebTestClient instance.
	 */
	@Autowired
	private WebTestClient testClient;
	
	/**
	 * ListRepository mock.
	 */
	@MockBean
	private ListRepository repo;
	
	/**
	 * Setting up the data and mock for tests.
	 */
	@Before
	public void setUp() {
		Task task = new Task("title", "desc", Date.from(Instant.now()), TaskStatus.NOT_COMPLETED, TaskPriority.MEDIUM, "category");
		Mockito.when(repo.findById("taskId")).thenReturn(Mono.just(task));
		Mockito.when(repo.findById("no_id")).thenReturn(Mono.empty());
		ToDoError error = new ToDoError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "Something unexpected happened");
		ToDoException exception = new ToDoException(error);
		Mockito.when(repo.findById("errorId")).thenReturn(Mono.error(exception));
		Mockito.when(repo.findAll()).thenReturn(Flux.just(task));
	}
	
	/**
	 * Test GET /v1/tasks/{task_id}.
	 */
	@Test
	public void testGetById() {
		testClient.get().uri("/v1/tasks/taskId").accept(APPLICATION_JSON)
			  .exchange()
			  .expectStatus().isOk().expectBody(Task.class);
	}
	
	/**
	 * Test GET /v1/tasks/{task_id} when no task exists with {task_id}.
	 */
	@Test
	public void testGetByIdTaskNotFound() {
		testClient.get().uri("/v1/tasks/no_id").accept(APPLICATION_JSON)
			  .exchange()
			  .expectStatus().isNotFound().expectBody(ToDoError.class);
	}
	
	/**
	 * Test GET /v1/tasks/{task_id} when any exception occurs.
	 */
	@Test
	public void testGetByIdWithException() {
		testClient.get().uri("/v1/tasks/errorId").accept(APPLICATION_JSON)
			  .exchange()
			  .expectStatus().is5xxServerError();
	}
	
	/**
	 * Test GET /v1/tasks.
	 */
	@Test
	public void testGetAll() {
		testClient.get().uri("/v1/tasks")
			  .exchange()
			  .expectStatus().isOk().expectBodyList(Task.class);
	}
	
	/**
	 * Test GET /v1/tasks when any exception occurs.
	 */
	@Test
	public void testGetAllWithException() {
		ToDoError error = new ToDoError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "Something unexpected happened");
		ToDoException exception = new ToDoException(error);
		Mockito.when(repo.findAll()).thenReturn(Flux.error(exception));
		testClient.get().uri("/v1/tasks")
			  .exchange()
			  .expectStatus().is5xxServerError();
	}

}
