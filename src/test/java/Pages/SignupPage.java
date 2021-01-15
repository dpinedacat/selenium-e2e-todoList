package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

  private WebDriver driver;

  @FindBy(tagName = "h1")
  WebElement heading;

  @FindBy(xpath = "//*[@id=\"app\"]/section/div/form/input[1]")
  private WebElement emailInput;


  @FindBy(xpath = "//*[@id=\"app\"]/section/div/form/input[2]")
  private WebElement passwordInput;

  @FindBy(className = "signup-button")
  private WebElement signUpButton;

  @FindBy(id = "errorMessage")
  private WebElement errorMessage;

  @FindBy(id = "loginMessage")
  private WebElement loginMessage;

  public SignupPage(WebDriver driver) {
    this.driver = driver;

    PageFactory.initElements(driver, this);
  }

  public void setEmail(String email) {
    emailInput.clear();
    emailInput.sendKeys(email);
  }

  public void setPassword(String password) {
    passwordInput.clear();
    passwordInput.sendKeys(password);
  }
  public String getEmail() {
    return emailInput.getAttribute("value");
  }
  public String getPassword() {
    return passwordInput.getAttribute("value");
  }

  public void clickSignupButton(){
    signUpButton.click();
  }

  public String getErrorMessage(){
    return errorMessage.getText();
  }
  public String getLoginMessage(){
    return loginMessage.getText();
  }

  public boolean isSignupPageOpened(){
    return heading.getText().toString().contains("signup");
  }
  public boolean isHomePageOpened(){
    return heading.getText().toString().contains("todos");
  }
  public void createTestUser(){
    setEmail("email@example.com");
    setPassword("abc123");
  }

  public void createTestUser2(){
    setEmail("email2@example.com");
    setPassword("abc123");
  }

  public void createTestUser3(){
    setEmail("email3@example.com");
    setPassword("abc123");
  }

//  public void resetAccounts(){
//    post("/reset");
//    given().contentType(ContentType.JSON).body("").post("/reset");
//  }

}
