package com.neeraj.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.todo.model.ItemOnList;
import com.neeraj.todo.model.ListViewModel;
import com.neeraj.todo.service.ListService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A rest controller to expose endpoint for items on the list. 
 * 
 * @author neeraj.kumar
 *
 */
@RestController
public class ListController {
	
	/**
	 * Injecting ListService instance.
	 */
	@Autowired
	private ListService listServ;
	
	/**
	 * Get the list of all the items.
	 * 
	 * @return Flux<ItemOnList>
	 */
	@RequestMapping("/")
	public Flux<ItemOnList> getAll() {
		return listServ.getItemsList();
	}
	
	/**
	 * Get the item of related to passed 'id'
	 * 
	 * @param id
	 * @return Mono<ItemOnList>
	 */
	@GetMapping("/{id}")
	public Mono<ItemOnList> getItemById(@PathVariable String id) {
		return listServ.getItemById(id);
	}
	
	@RequestMapping("/add")
	public String addItem(@ModelAttribute ItemOnList requestItem) {
		listServ.addItem(requestItem);
		return "redirect:/";
	}
	
	/**
	 * Add the new item in the database.
	 * 
	 * @param requestLists
	 * @return String
	 */
	@RequestMapping("/update")
	public String updateItems(@ModelAttribute ListViewModel requestLists) {
		listServ.updateItem(requestLists);
		return "redirect:/";
	}	

}
