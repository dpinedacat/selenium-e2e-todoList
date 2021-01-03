package TodoListTests;

import Pages.HomePage;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//    GIVEN I am at the todo page
//    AND there is an item on the list
//    WHEN I hover over the item
//    AND X out on the item
//    THEN the item is removed
public class CrossOutItem {

  static WebDriver driver;

  @BeforeClass
  public static void BeforeMethod() throws IOException {
    System.setProperty("webdriver.chrome.driver", "C://ChromeDriver/chromedriver.exe");
    driver = new ChromeDriver();

    driver.navigate().to("http://localhost:3000/");
  }


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


  }

  @AfterClass
  public static void closeBrowser() {
    //clear out list
    HomePage list = new HomePage(driver);
    list.deleteItem(list.getTodoList().get(0));
    //quit the driver
    driver.close();
    driver.quit();
  }

}



