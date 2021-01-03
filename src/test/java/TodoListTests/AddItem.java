package TodoListTests;

import static org.junit.Assert.assertEquals;

import Pages.HomePage;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


//    GIVEN I am at the todo page
//    WHEN I add text then press enter
//    THEN It is added to the list
public class AddItem {
  static WebDriver driver;

  @BeforeClass
  public static void BeforeMethod() throws IOException {
    System.setProperty("webdriver.chrome.driver", "C://ChromeDriver/chromedriver.exe");
    driver = new ChromeDriver();

    driver.navigate().to("http://localhost:3000/");
  }

  @Test //annotation allows the test runner know that this is a test
  public void addNewItem(){
    //Create object of ItemsAddedPage class
    HomePage list = new HomePage(driver);

    //fill up data
    list.setNewItem("Buy more milk");

    //verify new item added to list
    assertEquals("Item was not found on the list", driver.findElement(By.cssSelector(".view"))
    .getText(), "Buy more milk");

  }

  @AfterClass
  public static void closeBrowser() {
    //clear out list
    HomePage list = new HomePage(driver);
    list.deleteItem(list.getTodoList().get(0));
    // quit the driver
    driver.close();
    driver.quit();
  }
}
