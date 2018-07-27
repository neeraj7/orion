package com.neeraj.orion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neeraj.orion.model.ItemOnList;
import com.neeraj.orion.model.ListViewModel;
import com.neeraj.orion.repository.ListRepository;

@Service
public class ListService {

	@Autowired
	private ListRepository listRepo;
	
	public List<ItemOnList> getItemsList() {
		List<ItemOnList> itemsList = (ArrayList<ItemOnList>) listRepo.findAll();
		return itemsList;
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
