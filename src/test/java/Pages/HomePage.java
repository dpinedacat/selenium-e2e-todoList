package Pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {

  private WebDriver driver;

  @FindBy(id = "add-todo")
  private WebElement newItem;

  @FindAll(@FindBy(className = "view"))
  private List<WebElement> todoList;

  @FindBy(className = "view")
  private WebElement todoItem;


  public HomePage(WebDriver driver) {
    this.driver = driver;

    PageFactory.initElements(driver, this);
  }

  public WebElement getNewItem() {
    return newItem;
  }

  public void setNewItem(String item) {
    newItem.clear();
    newItem.sendKeys(item);
    newItem.sendKeys(Keys.ENTER);
  }

  public List<WebElement> getTodoList() {
    return todoList;
  }

  public void setTodoList(List<WebElement> todoList) {
    this.todoList = todoList;
  }

  public String getTodoItem() {
    return todoItem.getText();

  }

  public void setTodoItem(WebElement todoItem) {
    this.todoItem = todoItem;
  }

  public void deleteItem(WebElement todoItem) {
    Actions action = new Actions(driver);

    WebElement itemHover = driver.findElement(By.cssSelector(".view"));
    //find the button for the item
    WebElement xButton = driver.findElement(By.className("destroy"));
    //perform action on the button
    action.moveToElement(itemHover).moveToElement(xButton).click().build().perform();
  }

  public void toggleItem(WebElement todoItem) {
    Actions action = new Actions(driver);

    //interrogate list
    WebElement itemHover = driver.findElement(By.cssSelector(".view"));
    //find the button for the item
    WebElement toggleButton = driver.findElement(By.className("toggle"));
    //perform action on the button
    action.moveToElement(itemHover).moveToElement(toggleButton).click().build().perform();
  }

  public boolean findItemInList(String text) {

    return driver.findElements(By.className("view")).stream()
        .anyMatch(todoItem -> todoItem.getText().equals(text));

  }
}
