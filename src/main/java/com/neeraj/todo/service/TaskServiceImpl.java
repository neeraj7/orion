package com.neeraj.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neeraj.todo.exceptions.ToDoException;
import com.neeraj.todo.model.Task;
import com.neeraj.todo.model.ToDoError;
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
public class TaskServiceImpl implements TaskService {

	/**
	 * Injecting the instance of ListRepository.
	 */
	@Autowired
	private ListRepository listRepo;

	/**
	 * Get the all the tasks in the database.
	 * 
	 * @return Flux<ItemOnList>
	 */
	public Flux<Task> getTasks() {
		return listRepo.findAll();
	}

	/**
	 * Create a to-do task in the database.
	 * 
	 * @param requestItem
	 * @return
	 */
	public Mono<Task> createTask(Task task) {
		return listRepo.save(task);
	}

	/**
	 * Update to-do task in the database.
	 * 
	 * @param requestLists
	 * @return
	 */
	public Mono<Task> updateTask(String task_id, Task task) {

		return listRepo.findById(task_id).flatMap(t -> {
			task.setCreatedDate(t.getCreatedDate());
			return listRepo.save(task);
		}).switchIfEmpty(Mono.error(new Exception("Task doesn't exist")));
	}

	/**
	 * Get the to-do task by task_id.
	 * 
	 * @param task_id
	 * @return Mono<ItemOnList>
	 */
	public Mono<Task> getTaskById(String id) {
		return listRepo.findById(id);
	}

	/**
	 * Delete the to-do task by task_id.
	 * 
	 * @param task_id
	 * @return Mono<Void>
	 */
	public Mono<Void> deleteTaskById(String id) {
		// find the task in the database
		return listRepo.findById(id)
				// returns an error if not found
				.switchIfEmpty(Mono.error(new ToDoException(new ToDoError(404, "Not Found", "Task doesn't exist"))))
				.flatMap(task -> {
					// delete the data if found
					return listRepo.delete(task);
				}).onErrorResume(e -> Mono.error((ToDoException) e));
	}

	/**
	 * Delete all tasks in database.
	 * 
	 * @return Mono<Void>
	 */
	public Mono<Void> deleteTasks() {
		return listRepo.deleteAll().onErrorResume(e -> Mono.error((ToDoException) e));
	}

}
