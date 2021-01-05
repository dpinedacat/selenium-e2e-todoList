package Tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.http.ContentType;
import org.junit.Test;

public class RestAssuredTests extends FunctionalTest {


  @Test
  public void basicPingTest() {
    given().when().get("/").then().statusCode(200);
  }


  @Test
  public void getAllTodos() {
    given().when().get("/todos").then().statusCode(200).and().contentType(ContentType.JSON);
  }

  @Test
  public void todoNotFound() {
    given().when().get("/todos/999").then().statusCode(404);
  }

  @Test
  public void createNewTodoItem() {

    String payload = "{\n" +
        "  \"title\": \"buy milk\",\n" +
        "  \"completed\": false\n" +
        "}";

    given().contentType(ContentType.JSON).body(payload).post("/todos").then().statusCode(201)
        .body("title", equalTo("buy milk"));

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

  }

  @Test
  public void deleteItemUsingIdNotFound(){
    given().pathParam("id",999).when().delete("/todos/{id}").then().statusCode(404);
  }

  @Test
  public void deleteAllItems(){
    //verify status code
    given().when().delete("/todos").then().statusCode(204);
    //verify json is body is empty array
    given().get("/todos").then().body(equalTo("[]"));

  }

}

