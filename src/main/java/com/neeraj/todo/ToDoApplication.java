package com.neeraj.todo;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.neeraj.todo.model.ItemOnList;
import com.neeraj.todo.repository.ListRepository;

@SpringBootApplication
public class ToDoApplication {

	@Bean
	CommandLineRunner initData(ListRepository listRepo) {
		Stream<ItemOnList> data = Stream.of(new ItemOnList("item 1"),
				new ItemOnList("item 2"),
				new ItemOnList("item 3"),
				new ItemOnList("item 4"));
		return args -> listRepo.deleteAll()
					           .subscribe(null, null, 
					        		   () -> data.forEach(item -> listRepo.save(item)
					        				   							  .subscribe(System.out::println)));
	}
	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}
}
