package com.neeraj.todo;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.neeraj.todo.model.Task;

@RunWith(SpringRunner.class)
@WebFluxTest
@Ignore
public class ToDoApplicationTest {

	@Autowired
	private WebTestClient client;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testGetById() {
		client.get().uri("/item/{id}", 2).accept(APPLICATION_JSON)
			  .exchange()
			  .expectStatus().isOk()
			  .expectBody(Task.class);
	}
	
	@Test
	public void testGetAll() {
		client.get().uri("items").accept(APPLICATION_JSON)
			  .exchange()
			  .expectStatus().isOk()
			  .expectBody(Task.class);
	}

}
