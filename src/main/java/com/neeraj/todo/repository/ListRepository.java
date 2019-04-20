package com.neeraj.todo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.neeraj.todo.model.Task;

/**
 * To-do items list repository. 
 * 
 * @author neeraj.kumar
 *
 */
public interface ListRepository extends ReactiveMongoRepository<Task, String> {

}
