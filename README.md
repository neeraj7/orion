# Orion - A simple to-do app
A simple web based to-do list using spring boot framework.

It uses _spring webflux_ framework with _reactive mongodb_.

### To run the application on your system

- Install and run the [mongodb](https://docs.mongodb.com/v3.2/tutorial/install-mongodb-on-windows/) on your local system.
- Clone the repo and import the project into your IDE.
- Run the application from the IDE.

### Instructions to use the application

- Any Web API testing tool can be used to send the request to the application running on the localhost. I am using [Postman](https://www.getpostman.com).

Note :- Please make sure you are passing 'Content-Type' header with the value 'application/json' in every request.

- Now, let's hit the [GET(/v1/tasks)](http://localhost:8080/v1/tasks) endpoint
    
    Response: 
    ```
    [
    {
        "id": "5de8e16e956e6d7f4cc00ea0",
        "title": "title",
        "desc": "desc",
        "status": "NOT_COMPLETED",
        "priority": "MEDIUM",
        "category": "local",
        "createdDate": "2019-12-05T10:52:30.779+0000"
    }
    ]
    ```
- [GET(/v1/tasks/{task_id})](http://localhost:8080/v1/tasks/5de8e16e956e6d7f4cc00ea0) to access the to-do task by its 'task_id'
    
    Response:
    ```
    {
        "id": "5de8e16e956e6d7f4cc00ea0",
        "title": "title",
        "desc": "desc",
        "status": "NOT_COMPLETED",
        "priority": "MEDIUM",
        "category": "local",
        "createdDate": "2019-12-05T10:52:30.779+0000"
    }
    ```
- [POST(/v1/tasks)](http://localhost:8080/v1/tasks) to create a new task in database 
   
    Request Body :
    ```
    {
   "title": "title",
    "desc": "desc",
    "status": "NOT_COMPLETED",
    "priority": "MEDIUM",
    "category": "local"
    }
    ```
    Response :
    ```
    {
    "id": "5de8e16e956e6d7f4cc00ea0",
    "title": "title",
    "desc": "desc",
    "status": "NOT_COMPLETED",
    "priority": "MEDIUM",
    "category": "local",
    "createdDate": "2019-12-05T10:52:30.779+0000"
    }
    ```
- [PUT(/v1/tasks/{task_id})](http://localhost:8080/v1/tasks/5de8e16e956e6d7f4cc00ea0) to update an existing to-do task
    
    Request Body :
    ```
    {
   "title": "title1",
    "desc": "desc",
    "status": "COMPLETED",
    "priority": "MEDIUM",
    "category": "local"
    }
    ```
    Response :
    ```
    {
    "id": "5de8e562956e6d7f4cc00ea1",
    "title": "title1",
    "desc": "desc",
    "status": "COMPLETED",
    "priority": "MEDIUM",
    "category": "local",
    "createdDate": "2019-12-05T11:09:22.076+0000"
    }
    ```
    
  [DELETE(/v1/tasks)](http://localhost:8080/v1/tasks) to delete all tasks in the database
    
    Response :
    ```
    Not yet decided.
    ```  

  [DELETE(/v1/tasks/{task_id})](http://localhost:8080/v1/tasks/5b8581c6f88af61440027373) to delete an existing to-do task
    
    Response :
    ```
    Not yet decided.
    ```  
