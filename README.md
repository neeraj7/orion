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

- Now, let's hit the [GET(/items)](http://localhost:8080/items) endpoint
    
    Response: 
    ```
    [
    {
        "id": "5b8581c6f88af61440027373",
        "name": "item 1",
        "complete": false
    },
    {
        "id": "5b8581c6f88af61440027375",
        "name": "item 3",
        "complete": false
    },
    {
        "id": "5b8581c6f88af61440027374",
        "name": "item 2",
        "complete": false
    },
    {
        "id": "5b8581c6f88af61440027376",
        "name": "item 4",
        "complete": false
    }
    ]
    ```
- [GET(/item/5b8581c6f88af61440027373)](http://localhost:8080/item/5b8581c6f88af61440027373) to access the to-do item by its 'id'
    
    Response:
    ```
    {
    "id": "5b8581c6f88af61440027373",
    "name": "item 1",
    "complete": false
    }
    ```
- [POST(/item/new)](http://localhost:8080/item/new) to add a new to-do item 
   
    Request Body :
    ```
    {
       "name":"New Task"
    }
    ```
    Response :
    ```
    {
    "id": "5b85834bf88af61440027377",
    "name": "New Task",
    "complete": false
    }
    ```
- [PUT(/item/update)](http://localhost:8080/item/update) to update an existing to-do item
    
    Request Body :
    ```
    {
    "id": "5b85834bf88af61440027377",
    "name": "New Task updated",
    "complete": true
    }
    ```
    Response :
    ```
    {
    "id": "5b85834bf88af61440027377",
    "name": "New Task updated",
    "complete": true
    }
    ```
    
    
