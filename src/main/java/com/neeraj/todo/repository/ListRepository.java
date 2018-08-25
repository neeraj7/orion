package com.neeraj.todo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.neeraj.todo.model.ItemOnList;

public interface ListRepository extends ReactiveMongoRepository<ItemOnList, Long> {

}
