package com.neeraj.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neeraj.todo.model.ItemOnList;
import com.neeraj.todo.model.ListViewModel;
import com.neeraj.todo.repository.ListRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A service layer between controller and repository.
 * 
 * @author neeraj.kumar
 *
 */
@Service
public class ListService {

	/**
	 * Injecting the instance of ListRepository.
	 */
	@Autowired
	private ListRepository listRepo;
	
	/**
	 * Get the all the tasks in the mongodb.
	 * 
	 * @return Flux<ItemOnList>
	 */
	public Flux<ItemOnList> getTasks() {
		return listRepo.findAll();
	}
	
	/**
	 * Create a to-do task in the database.
	 * 
	 * @param requestItem
	 */
	public void createTask(ItemOnList requestItem) {
		ItemOnList item = new ItemOnList(requestItem.getName());
		listRepo.save(item);
	}
	
	/**
	 * Update to-do task in the database.
	 * 
	 * @param requestLists
	 */
	public void updateTask(ListViewModel requestLists) {
		for(ItemOnList requestItem : requestLists.getItemLists()) {
			ItemOnList item = new ItemOnList(requestItem.getName());
            item.setComplete(requestItem.isComplete());
            item.setId(requestItem.getId());
            listRepo.save(item);
		}
	}

	/**
	 * Get the to-do task by the id.
	 * 
	 * @param id
	 * @return Mono<ItemOnList>
	 */
	public Mono<ItemOnList> getTaskById(String id) {
		return listRepo.findById(id);
	}
}
