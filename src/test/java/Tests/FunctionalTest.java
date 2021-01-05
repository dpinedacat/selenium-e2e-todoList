package Tests;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;

public class FunctionalTest {


  @BeforeClass
  public static void setup() {
    String port = System.getProperty("server.port");
    if (port == null) {
      RestAssured.port = Integer.valueOf(3000);
    }
    else{
      RestAssured.port = Integer.valueOf(port);
    }


    String baseHost = System.getProperty("server.host");
    if(baseHost==null){
      baseHost = "http://localhost";
    }
    RestAssured.baseURI = baseHost;

//pre load data to list before running tests
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
  }


//  @Test
//  public void makeSureHomePageIsUp() {
//    given().when().get("http://localhost:3000").then().statusCode(200);
//  }



}
