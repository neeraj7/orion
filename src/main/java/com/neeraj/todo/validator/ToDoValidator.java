package com.neeraj.todo.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.neeraj.todo.exceptions.ToDoException;
import com.neeraj.todo.model.ToDoError;

import reactor.core.publisher.Mono;

/**
 * ToDoValidator class.
 * 
 * @author Neeraj
 *
 */
@Component
public class ToDoValidator {
	
	/**
	 * Hibernate validator instance as default used by spring.
	 */
	private Validator validator = javax.validation.Validation.buildDefaultValidatorFactory()
															 .getValidator();

	/**
	 * To validate the request body
	 * 
	 * @param bodyClass, class type of the request body
	 * @param request, service request
	 * @return request body when there is no validation errors
	 *         list of errors when there is/are any errors in the request body
	 */
	public <T> Mono<T> validateBody(Class<T> bodyClass, ServerRequest request) {
		return request.bodyToMono(bodyClass)
					  .flatMap(body -> {
						  List<ToDoError> errors = prepareErrors(validator.validate(body));
						  if(errors.isEmpty())
							  return Mono.just(body);
						  else {
							  return Mono.error(new ToDoException(errors));
						  }
					  });
	}
	
	/**
	 * Prepare the list of to-do errors for any present validation error.
	 * 
	 * @param error
	 * @return
	 */
	private <T> List<ToDoError> prepareErrors(Set<ConstraintViolation<T>> error) {
		List<ToDoError> errors = new ArrayList<>();
		error.forEach(e -> errors.add(new ToDoError("Validation Error", e.getPropertyPath() + " " + e.getMessage())));
		if(errors.isEmpty())
			return Collections.emptyList();
		else
			return errors;
	}

}
