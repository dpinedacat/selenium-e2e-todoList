## SDET Capstone Project - Todo List Testing

#### [Requirements Specifications (SRS & User Stories)](https://docs.google.com/document/d/1tAbYqeIHtvmGi213qa-96b0Z5ntbx_blQh7fjKgur3E/edit?usp=sharing)
#### [Test Plan](https://docs.google.com/document/d/1eItVlCqdWSt3oH4TBR_kqew5ZWw0jYQKTYfLongx5bI/edit?usp=sharing)
#### [Capstone Functional Requirements](https://docs.google.com/document/d/1J6dbCcXrX6uprZc2gRZx30behGbY9d_eljjhi-SbtDg/edit?usp=sharing)
#### [Defect Report](https://docs.google.com/document/d/1opNwyDQ7BgyL_2lfo_qGtPovjUSRMyGabTmUe0xXDZE/edit?usp=sharing)


### Pre Requisites
- [Original repo link](https://github.com/dpinedacat/todoList-testing)
- Import into IntelliJ as Maven project.


### TodoMVC app
Simple todoMVC app build with Vue.js [TodoMVC App](https://github.com/filiphric/udemy-cypress-course).

### How to install and run app
1. `npm install`
2. `npm start`
3. open app on http://localhost:3000



***Postman Usage***

- When running and testing postman, go through it in order, be sure to re-run the application after completing folder, to avoid issues after deletions. 

- The complete postman collection for this example can be found here: 

https://www.getpostman.com/collections/f33c43ef9aaef2530e48

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/f33c43ef9aaef2530e48)


---
#### GET /todos
Returns an array of all todo items.

---
#### POST /todos
Creates a todo item. 

**Example of a an item payload:**
```json
{
  "title": "buy milk",
  "completed": false,
  "id": 1
}
```

---
#### PATCH /todos/{id}
Edits todo item, usually to change `completed` state. {id} stands for todo id.

**Example payload:**
```json
{
  "completed": true
}
```
---


#### DELETE /todos/{id}
Deletes todo item with given id.

---
#### DELETE /todos
Deletes all todos.

---
#### POST /todos/seed
Seed an array of todos. Payload needs to be an array of objects, containing todos. This request rewrites all todos.

---
#### POST /signup
Creates a new account. 
**Example payload:**
```json
{
  "email": "email@example.com",
  "password": "abc123"
}
```
**Example error statuses:**

`409 (Conflict)` - Account already exists

`401 (Unauthorized)` - Email or password was not provided

**Request headers:**

`sendwelcomeemail: true` - Sends welcome email to signed up user

**Response headers:**

`Set-Cookie: "auth=true;"`

---
#### POST /login
Logs into a new account.

**Example payload:**
```json
{
  "email": "email@example.com",
  "password": "abc123"
}
```
**Response headers:**

`Set-Cookie: "auth=true;"`

**Example error statuses:**

`401 (Unauthorized)` - Wrong email or password

---
#### POST /reset
Deletes all todos and all accounts.

---
#### DELETE /accounts
Deletes all accounts.

---
#### POST /accounts/seed
Seed an array of accounts. Payload needs to be an array of objects, containing accounts. This request rewrites all accounts.
