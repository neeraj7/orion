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

@RestController
public class ListController {
	
	@Autowired
	private ListService listServ;
	
	@RequestMapping("/")
	public Flux<ItemOnList> index() {
		return listServ.getItemsList();
	}
	
	@GetMapping("/{id}")
	public Mono<ItemOnList> getItemById(@PathVariable String id) {
		return listServ.getItemById(id);
	}
	
	@RequestMapping("/add")
	public String addItem(@ModelAttribute ItemOnList requestItem) {
		listServ.addItem(requestItem);
		return "redirect:/";
	}
	
	@RequestMapping("/update")
	public String updateItems(@ModelAttribute ListViewModel requestLists) {
		listServ.updateItem(requestLists);
		return "redirect:/";
	}
	

}
