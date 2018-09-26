package com.neeraj.orion.todo;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.neeraj.todo.model.ItemOnList;

@RunWith(SpringRunner.class)
@WebFluxTest
public class ToDoApplicationTests {

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
			  .expectBody(ItemOnList.class);
	}
	
	@Test
	public void testGetAll() {
		client.get().uri("items").accept(APPLICATION_JSON)
			  .exchange()
			  .expectStatus().isOk()
			  .expectBody(ItemOnList.class);
	}

}
