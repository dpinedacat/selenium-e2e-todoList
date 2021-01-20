package Tests;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class RestAssuredTests extends FunctionalTest {

  @Test
  public void basicPingTest() {
    given().when().get("/").then().statusCode(200);
    cleanUp();
  }

  @Test
  public void getAllTodos() {
    given().when().get("/todos").then().statusCode(200).and().contentType(JSON);
    cleanUp();
  }

  @Test
  public void todoNotFound() {
    given().when().get("/todos/999").then().statusCode(404);
    cleanUp();
  }

  @Test
  public void createNewTodoItem() {

    String payload = "{\n" +
        "  \"title\": \"buy milk\",\n" +
        "  \"completed\": false\n" +
        "}";

    given().contentType(JSON).body(payload).post("/todos").then().statusCode(201)
        .body("title", equalTo("buy milk"));
    cleanUp();
  }

  @Test
  public void deleteItemUsingId() {

    String payload = "{\n" +
        "  \"title\": \"delete this!\",\n" +
        "  \"completed\": false,\n" +
        "  \"id\": 30\n" +
        "}";

    given().contentType(JSON).body(payload).post("/todos").then().statusCode(201)
        .body("title", equalTo("delete this!"));
    ;

    given().pathParam("id", 30).when().delete("/todos/{id}").then().statusCode(200);
    cleanUp();

  }

  @Test
  public void deleteItemUsingIdNotFound() {
    given().pathParam("id", 999).when().delete("/todos/{id}").then().statusCode(404);
    cleanUp();

  }

  @Test
  public void deleteAllItems() {
    String item1 = "{\n" +
        "  \"title\": \"take notes\",\n" +
        "  \"completed\": false\n" +
        "}";
    String item2 = "{\n" +
        "  \"title\": \"fold laundry\",\n" +
        "  \"completed\": false\n" +
        "}";
    String item3 = "{\n" +
        "  \"title\": \"make dinner\",\n" +
        "  \"completed\": false\n" +
        "}";

    given().contentType(JSON).body(item1).post("/todos");
    given().contentType(JSON).body(item2).post("/todos");
    given().contentType(JSON).body(item3).post("/todos");
    //verify status code
    given().when().delete("/todos").then().statusCode(204);
    //verify json is body is empty array
    given().get("/todos").then().body(equalTo("[]"));
    cleanUp();

  }

  @Test
  public void newSignup() {
    cleanUpAccounts();

    String payload = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"abc123\"\n" +
        "}";

    //verify user is created
    given().contentType(JSON).body(payload).post("/signup").then().statusCode(201)
        .body("email", equalTo("email@example.com"));

    cleanUpAccounts();
  }

  @Test
  public void newSignupConflict() {
    cleanUpAccounts();

    String payload = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"abc123\"\n" +
        "}";

    //verify initial user is created
    given().contentType(JSON).body(payload).post("/signup").then().statusCode(201)
        .body("email", equalTo("email@example.com"));

    //verify attempt to recreate user throws conflict
    given().contentType(JSON).body(payload).post("/signup").then().statusCode(409);

    cleanUpAccounts();
  }

  @Test
  public void newSignupUnauthorized() {
    cleanUpAccounts();

    //missing email
    String payload = "{\n" +
        "  \"email\": \"email@example.com\"\n" +
        "}";

    //verify user is unauthorized
    given().contentType(JSON).body(payload).post("/signup").then().statusCode(401);

    cleanUpAccounts();
  }

  @Test
  public void validLogin() {
    cleanUpAccounts();

    //initiate new user
    String payload = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"abc123\"\n" +
        "}";

    //verify user is created
    given().contentType(JSON).body(payload).post("/signup").then().statusCode(201)
        .body("email", equalTo("email@example.com"));

    //verify user is logged in
    given().contentType(JSON).body(payload).post("/login").then().statusCode(200);

    cleanUpAccounts();
  }

  @Test
  public void invalidLogin() {
    cleanUpAccounts();

    //initialize new user
    String payload = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"abc123\"\n" +
        "}";

    //verify user is created
    given().contentType(JSON).body(payload).post("/signup").then().statusCode(201)
        .body("email", equalTo("email@example.com"));

    //wrong password input

    String wrongPayload = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"wrongPassword\"\n" +
        "}";
    given().contentType(JSON).body(wrongPayload).post("/login").then().statusCode(401);
    cleanUpAccounts();
  }

  @Test
  public void resetAll() {
    cleanUpAccounts();

    //initialize new user
    String payload = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"abc123\"\n" +
        "}";

    String item1 = "{\n" +
        "  \"title\": \"take notes\",\n" +
        "  \"completed\": false\n" +
        "}";
    String item2 = "{\n" +
        "  \"title\": \"fold laundry\",\n" +
        "  \"completed\": false\n" +
        "}";
    String item3 = "{\n" +
        "  \"title\": \"make dinner\",\n" +
        "  \"completed\": false\n" +
        "}";

    //verify user is created
    given().contentType(JSON).body(payload).post("/signup").then().statusCode(201)
        .body("email", equalTo("email@example.com"));

    //add new todos
    given().contentType(JSON).body(item1).post("/todos");
    given().contentType(JSON).body(item2).post("/todos");
    given().contentType(JSON).body(item3).post("/todos");

    //verify all todos and accounts are deleted
    given().contentType(JSON).body("").post("/reset").then().statusCode(204);

    //verify accounts and todos are empty
    given().get("/accounts").then().body(equalTo("[]"));
    given().get("/todos").then().body(equalTo("[]"));

    cleanUpAccounts();

  }

  @Test
  public void deleteAllAccounts() {
    cleanUpAccounts();

    //initialize new users
    String user1 = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"abc123\"\n" +
        "}";

    String user2 = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"abc123\"\n" +
        "}";

    String user3 = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"abc123\"\n" +
        "}";

    //verify user is created
    given().contentType(JSON).body(user1).post("/signup");
    given().contentType(JSON).body(user2).post("/signup");
    given().contentType(JSON).body(user3).post("/signup");

    //verify all accounts are deleted
    given().when().delete("/accounts").then().statusCode(204);
    //verify accounts are empty array
    given().get("/accounts").then().body(equalTo("[]"));

    cleanUpAccounts();

  }

  @Test
  public void patchTodoById() {
    cleanUp();
    cleanUpAccounts();

    //initiate initial todo payload
    String payload = "{\n" +
        "  \"title\": \"make dinner\",\n" +
        "  \"completed\": false ,\n" +
        "  \"id\": 1\n" +
        "}";

    //updated data
    String toggleComplete = "{\n" +
        "  \"completed\": true\n" +
        "}";

    //verify payload is posted
    given().contentType(JSON).body(payload).post("/todos").then().statusCode(201)
        .body("title", equalTo("make dinner"));

    //verify payload completion is updated
    given().contentType(JSON).body(toggleComplete).patch("/todos/1").then().statusCode(200)
        .body("completed", equalTo(true));

    cleanUp();
    cleanUpAccounts();
  }

  @Test
  public void seedTodos() {
    cleanUp();
    cleanUpAccounts();

    //initialize todo items
    Map<String, Object> item1 = new HashMap<String, Object>();
    item1.put("title", "Seed 1");
    item1.put("completed", false);
    item1.put("id", 1);

    Map<String, Object> item2 = new HashMap<String, Object>();
    item2.put("title", "Seed 2");
    item2.put("completed", false);
    item2.put("id", 2);

    Map<String, Object> item3 = new HashMap<String, Object>();
    item3.put("title", "Seed 3");
    item3.put("completed", false);
    item3.put("id", 3);

    List<Map<String, Object>> jsonArrayPayLoad = new ArrayList<>();

    //add todos to array list
    jsonArrayPayLoad.add(item1);
    jsonArrayPayLoad.add(item2);
    jsonArrayPayLoad.add(item3);

    //verify todos are seeded
    given().contentType(ContentType.JSON).body(jsonArrayPayLoad).post("/todos/seed").then().statusCode(201);

    //verify correct amount of todos are seeded
    given().when().get("/todos").then().statusCode(200).body("size()", is(3));
    Assert.assertEquals("Incorrect number of seeded items",jsonArrayPayLoad.size(), 3);

    cleanUp();
    cleanUpAccounts();
  }

  @Test
  public void seedAccounts() {
    cleanUp();
    cleanUpAccounts();

    //initialize new users
    Map<String, Object> user1 = new HashMap<String, Object>();
    user1.put("email", "email1@example.com");
    user1.put("password", "abc123");

    Map<String, Object> user2 = new HashMap<String, Object>();
    user2.put("email", "email2@example.com");
    user2.put("password", "abc123");

    Map<String, Object> user3 = new HashMap<String, Object>();
    user3.put("email", "email3@example.com");
    user3.put("password", "abc123");

    List<Map<String, Object>> jsonArrayPayLoad = new ArrayList<>();

    //add users to array list
    jsonArrayPayLoad.add(user1);
    jsonArrayPayLoad.add(user2);
    jsonArrayPayLoad.add(user3);

    //verify accounts are seeded
    given().contentType(ContentType.JSON).body(jsonArrayPayLoad).post("/accounts/seed").then().statusCode(201);

    //verify correct number of accounts are seeded
    given().when().get("/accounts").then().statusCode(200).body("size()", is(3));
    Assert.assertEquals("Incorrect number of seeded accounts",jsonArrayPayLoad.size(), 3);

    cleanUp();
    cleanUpAccounts();
  }

  public static void cleanUp() {
    delete("/todos");
  }

  public static void cleanUpAccounts() {
    delete("/accounts");
  }

}

