package TodoListTests;

import Pages.HomePage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


//    GIVEN I am at the todo page
//    WHEN I hover over an item
//    AND I click on the X next to the item
//    THEN the list will collapse
//    AND not reorder the list
public class DeleteItemCollapseTest {
  static WebDriver driver;

  @BeforeClass
  public static void BeforeMethod() throws IOException {
    System.setProperty("webdriver.chrome.driver", "C://ChromeDriver/chromedriver.exe");
    driver = new ChromeDriver();

    driver.navigate().to("http://localhost:3000/");

  }


  @Test //annotation allows the test runner know that this is a test
  public void deleteItemFromListCollapseTest(){
    //Create object of ItemsAddedPage class
    HomePage list = new HomePage(driver);

    //fill up data
    list.setNewItem("Ooops");
    list.setNewItem("Study");
    list.setNewItem("Workout");

    //grab original list
    List<WebElement> todoList = list.getTodoList();

    //create new updated list for comparison
    List<WebElement> updatedList = new ArrayList<>();
    for (int i = 1; i < todoList.size(); i++){
      updatedList.add(todoList.get(i));
    }

    //grab item to be deleted
    WebElement item = list.getTodoList().get(0);
    String deletedItem =item.getText();
    //delete item
    list.deleteItem(item);
    //verify item was deleted
    Assert.assertEquals(deletedItem, "Ooops");

    //verify list order did not change
    for (int i = 0; i < updatedList.size(); i++){
      Assert.assertEquals(updatedList.get(i), todoList.get(i));
    }
  }

  @AfterClass
  public static void closeBrowser() {
    //clear out list
    HomePage list = new HomePage(driver);
    list.deleteItem(list.getTodoList().get(0));
    list.deleteItem(list.getTodoList().get(0));
    // quit the driver
    driver.close();
    driver.quit();
  }
}
