package com.neeraj.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A simple to-do application.
 * 
 * @author neeraj.kumar
 *
 */
@SpringBootApplication
public class ToDoApplication {

	/**
	 * Initializing dummy data in database.
	 * 
	 * @param listRepo
	 * @return CommandLineRunner
	 */
	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}
}
