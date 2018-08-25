package com.neeraj.todo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A model to hold to-do item data.
 * 
 * @author neeraj.kumar
 *
 */
@Document
public class ItemOnList {
	
	/**
	 * Auto-generated id.
	 */
	@Id
	private String id;
	
	/**
	 * Name of the to-do item.
	 */
	private String name;
	
	/**
	 * Flag to denote whether the to-do item is complete or not.
	 */
	private boolean isComplete;
	
	/**
	 * Constructor. 
	 * 
	 * @param name
	 */
	public ItemOnList(String name) {
		this.name = name;
		this.isComplete = false;
	}

	/**
	 * NoArgsConstructor
	 */
	public ItemOnList() {
	}

	/**
	 * Get the to-do item id.
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Get the to-do item name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the to-do item name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the status of the to-do item.
	 * 
	 * @return boolean
	 */
	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * Set the status of the to-do item.
	 * 
	 * @param isComplete
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * Set the id of the to-do item.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

}
