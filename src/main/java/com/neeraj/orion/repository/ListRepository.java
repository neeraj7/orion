package com.neeraj.orion.repository;

import org.springframework.data.repository.CrudRepository;

import com.neeraj.orion.model.ItemOnList;

public interface ListRepository extends CrudRepository<ItemOnList, Long> {

}
