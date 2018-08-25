package com.neeraj.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neeraj.todo.model.ItemOnList;
import com.neeraj.todo.model.ListViewModel;
import com.neeraj.todo.repository.ListRepository;

import reactor.core.publisher.Flux;

@Service
public class ListService {

	@Autowired
	private ListRepository listRepo;
	
	public Flux<ItemOnList> getItemsList() {
		return listRepo.findAll();
	}
	
	public void addItem(ItemOnList requestItem) {
		ItemOnList item = new ItemOnList(requestItem.getName());
		listRepo.save(item);
	}
	
	public void updateItem(ListViewModel requestLists) {
		for(ItemOnList requestItem : requestLists.getItemLists()) {
			ItemOnList item = new ItemOnList(requestItem.getName());
            item.setComplete(requestItem.isComplete());
            item.setId(requestItem.getId());
            listRepo.save(item);
		}
	}
}
