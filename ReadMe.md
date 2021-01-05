## Todo List REST Assured API Testing

### Pre Requisites

- Import into IntelliJ as Maven project.


***Postman Usage***

- When running and testing postman, go through it in order, be sure to re-run the application after completing folder, to avoid issues after deletions. 

- The complete postman collection for this example can be found here: 

https://www.getpostman.com/collections/f33c43ef9aaef2530e48

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/f33c43ef9aaef2530e48)

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

#### DELETE /todos/{id}
Deletes todo item with given id.

---
#### DELETE /todos
Deletes all todos.
