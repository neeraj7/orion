package com.neeraj.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * A simple to-do application.
 * 
 * @author neeraj.kumar
 *
 */
@SpringBootApplication
@EnableMongoAuditing
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
