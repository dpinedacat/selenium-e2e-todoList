package Tests;

import static org.junit.Assert.assertEquals;

import Pages.HomePage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class TodoListTests {
  static WebDriver driver;

  @BeforeClass
  public static void BeforeMethod() throws IOException {
    System.setProperty("webdriver.chrome.driver", "C://ChromeDriver/chromedriver.exe");
    driver = new ChromeDriver();

    driver.navigate().to("http://localhost:3000/");
  }


  /*
  GIVEN I am at the todo page
  WHEN I add text then press enter
  THEN It is added to the list
   */
  @Test //annotation allows the test runner know that this is a test
  public void addNewItem(){
    //Create object of ItemsAddedPage class
    HomePage list = new HomePage(driver);

    //fill up data
    list.setNewItem("Buy more milk");

    //verify new item added to list
    assertEquals("Item was not found on the list", driver.findElement(By.cssSelector(".view"))
    .getText(), "Buy more milk");

    //clear out list
    list.deleteItem(list.getTodoList().get(0));

  }
  /*
  GIVEN I am at the todo page
  AND there is an item on the list
  WHEN I hover over the item
  AND X out on the item
  THEN the item is removed
  */
  @Test //annotation allows the test runner know that this is a test
  public void crossItemOutFromList() throws InterruptedException {
    //Create object of ItemsAddedPage class
    HomePage list = new HomePage(driver);

    //fill up data
    list.setNewItem("Laundry finished!");

    //grab item from todo list
    WebElement item = list.getTodoList().get(0);

    //perform toggle on item
    list.toggleItem(item);
    //wait for css values to update
    Thread.sleep(1500);

    //grab updated label values
    WebElement label = driver.findElement(By.xpath("//*[@id=\"todo-list\"]/li/div/label"));
    String lineThrough = label.getCssValue("text-decoration-line");
    String color = label.getCssValue("color");

    //verify expected css values
    Assert.assertEquals("line-through", lineThrough);
    Assert.assertEquals("rgba(217, 217, 217, 1)", color);

    //clear out list
    list.deleteItem(list.getTodoList().get(0));

  }

  /*
  GIVEN I am at the todo page
  WHEN I click on the circle next to the item
  THEN the item is crossed out
  AND the item is greyed out
  */
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

    //verify item was deleted and is no longer part of the list
    Assert.assertFalse("Item was not deleted", list.findItemInList(deletedItem));

  }

  /*
  GIVEN I am at the todo page
  WHEN I hover over an item
  AND I click on the X next to the item
  THEN the list will collapse
  AND not reorder the list
   */
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
    //verify item was deleted and is no longer part of the list
    Assert.assertFalse("Item was not deleted", list.findItemInList(deletedItem));

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
