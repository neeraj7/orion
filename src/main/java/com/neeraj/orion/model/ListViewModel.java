package com.neeraj.orion.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

public class ListViewModel {

	@Valid
	private List<ItemOnList> itemLists = new ArrayList<>();
	
	public ListViewModel(List<ItemOnList> itemLists) {
		this.itemLists = itemLists;
	}

	public List<ItemOnList> getItemLists() {
		return itemLists;
	}

	public void setItemLists(List<ItemOnList> itemLists) {
		this.itemLists = itemLists;
	}
}
