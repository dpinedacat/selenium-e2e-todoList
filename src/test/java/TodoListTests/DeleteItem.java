package TodoListTests;

import Pages.HomePage;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


//    GIVEN I am at the todo page
//    WHEN I click on the circle next to the item
//    THEN the item is crossed out
//    AND the item is greyed out

public class DeleteItem {
  static WebDriver driver;

  @BeforeClass
  public static void BeforeMethod() throws IOException {
    System.setProperty("webdriver.chrome.driver", "C://ChromeDriver/chromedriver.exe");
    driver = new ChromeDriver();

    driver.navigate().to("http://localhost:3000/");
  }


  @Test //annotation allows the test runner know that this is a test
  public void deleteItemFromList(){
    //Create object of ItemsAddedPage class
    HomePage list = new HomePage(driver);

    //fill up data
    list.setNewItem("Ooops");

    //grab item to be deleted
    WebElement item = list.getTodoList().get(0);
    String deletedItem =item.getText();
    //delete item
    list.deleteItem(item);

    //verify expected item was deleted
    Assert.assertEquals(deletedItem, "Ooops");


  }

  @AfterClass
  public static void closeBrowser() {
    // quit the driver
    driver.close();
    driver.quit();
  }
}
