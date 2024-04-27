# User Management REST API

This Spring Boot application provides RESTful API endpoints for managing user resources.

## Endpoints

### Create User
Can't save users younger the years than in .property file
- **POST** `/users`
    - Create a new user.
    - Request Body: RegistrationRequest
    - Response: UserDto

### Update User
Can update one field or all fields Users
Can't update the users birthDate younger the years than in .property file
- **PUT** `/users/update`
    - Update an existing user.
    - Request Body: UserDto
    - Response: Success message

### Delete User
- **DELETE** `/users/delete/{id}`
    - Delete a user by ID.
    - Path Variable: User ID
    - Response: Success message

### Search Users by Birth Date Range
- **GET** `/users/search`
    - Search users by birth date range.
    - Request Parameters:
        - `from` (start date)
        - `to` (end date)
    - Response: List of UserDto

### Get All Users
- **GET** `/users`
    - Retrieve all users.
    - Response: List of UserDto

## Request and Response Formats

#### RegistrationRequest JSON
````
{
"user":{
"email":"mail@mail",
"firstName":"firstName",
"lastName":"testLastName",
"birthDay":"year-month-day",
"address":"address",
"phone":"phone"
},
"password": "password"
}
````

#### UserDto JSON
````
{
"id": 1 //requared field
"email":"mail@mail",
"firstName":"firstName",
"lastName":"testLastName",
"birthDay":"year-month-day",
"address":"address",
"phone":"phone"
}
````