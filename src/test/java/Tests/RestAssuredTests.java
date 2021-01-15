package Tests;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.http.ContentType;
import org.junit.Test;

public class RestAssuredTests extends FunctionalTest {

  @Test
  public void basicPingTest() {
    given().when().get("/").then().statusCode(200);
    cleanUp();
  }

  @Test
  public void getAllTodos() {
    given().when().get("/todos").then().statusCode(200).and().contentType(ContentType.JSON);
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

    given().contentType(ContentType.JSON).body(payload).post("/todos").then().statusCode(201)
        .body("title", equalTo("buy milk"));
    cleanUp();
  }

  @Test
  public void deleteItemUsingId(){

    String payload = "{\n" +
        "  \"title\": \"delete this!\",\n" +
        "  \"completed\": false,\n" +
        "  \"id\": 30\n" +
        "}";

    given().contentType(ContentType.JSON).body(payload).post("/todos").then().statusCode(201)
        .body("title", equalTo("delete this!"));;

    given().pathParam("id",30).when().delete("/todos/{id}").then().statusCode(200);
    cleanUp();

  }

  @Test
  public void deleteItemUsingIdNotFound(){
    given().pathParam("id",999).when().delete("/todos/{id}").then().statusCode(404);
    cleanUp();

  }

  @Test
  public void deleteAllItems(){
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

    given().contentType(ContentType.JSON).body(item1).post("/todos");
    given().contentType(ContentType.JSON).body(item2).post("/todos");
    given().contentType(ContentType.JSON).body(item3).post("/todos");
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
    given().contentType(ContentType.JSON).body(payload).post("/signup").then().statusCode(201)
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
    given().contentType(ContentType.JSON).body(payload).post("/signup").then().statusCode(201)
        .body("email", equalTo("email@example.com"));

    //verify attempt to recreate user throws conflict
    given().contentType(ContentType.JSON).body(payload).post("/signup").then().statusCode(409);

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
    given().contentType(ContentType.JSON).body(payload).post("/signup").then().statusCode(401);

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
    given().contentType(ContentType.JSON).body(payload).post("/signup").then().statusCode(201)
        .body("email", equalTo("email@example.com"));

    //verify user is logged in
    given().contentType(ContentType.JSON).body(payload).post("/login").then().statusCode(200);

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
    given().contentType(ContentType.JSON).body(payload).post("/signup").then().statusCode(201)
        .body("email", equalTo("email@example.com"));

    //wrong password input

    String wrongPayload = "{\n" +
        "  \"email\": \"email@example.com\",\n" +
        "  \"password\": \"wrongPassword\"\n" +
        "}";
    given().contentType(ContentType.JSON).body(wrongPayload).post("/login").then().statusCode(401);
    cleanUpAccounts();
  }

  @Test
  public void resetAll(){
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
    given().contentType(ContentType.JSON).body(payload).post("/signup").then().statusCode(201)
        .body("email", equalTo("email@example.com"));

    //add new todos
    given().contentType(ContentType.JSON).body(item1).post("/todos");
    given().contentType(ContentType.JSON).body(item2).post("/todos");
    given().contentType(ContentType.JSON).body(item3).post("/todos");

    //verify all todos and accounts are deleted
    given().contentType(ContentType.JSON).body("").post("/reset").then().statusCode(204);

    //verify accounts and todos are empty
    given().get("/accounts").then().body(equalTo("[]"));
    given().get("/todos").then().body(equalTo("[]"));

    cleanUpAccounts();

  }

@Test
  public void deleteAllAccounts(){
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
    given().contentType(ContentType.JSON).body(user1).post("/signup");
    given().contentType(ContentType.JSON).body(user2).post("/signup");
    given().contentType(ContentType.JSON).body(user3).post("/signup");

  //verify all accounts are deleted
  given().when().delete("/accounts").then().statusCode(204);
  //verify accounts are empty array
  given().get("/accounts").then().body(equalTo("[]"));

    cleanUpAccounts();

  }

  public static void cleanUp(){
    delete("/todos");
  }
  public static void cleanUpAccounts(){
    delete("/accounts");
  }

}

