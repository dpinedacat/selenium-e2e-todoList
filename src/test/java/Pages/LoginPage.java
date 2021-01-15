package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

  private WebDriver driver;

  @FindBy(tagName = "h1")
  WebElement heading;

  @FindBy(xpath = "//*[@id=\"app\"]/section/div/form/input[1]")
  private WebElement emailInput;


  @FindBy(xpath = "//*[@id=\"app\"]/section/div/form/input[2]")
  private WebElement passwordInput;

  @FindBy(className = "login-button")
  private WebElement loginButton;

  @FindBy(id = "errorMessage")
  private WebElement errorMessage;

  @FindBy(id = "loginMessage")
  private WebElement loginMessage;

  public LoginPage(WebDriver driver) {
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

  public void clickLoginButton(){
    loginButton.click();
  }

  public String getErrorMessage(){
    return errorMessage.getText();
  }
  public String getLoginMessage(){
    return loginMessage.getText();
  }

  public boolean isLoginPageOpened(){
    return heading.getText().toString().contains("login");
  }
  public boolean isHomePageOpened(){
    return heading.getText().toString().contains("todos");
  }

//  public void resetAccounts(){
//    driver.get("http://localhost:3000/reset");
//    HttpPost httpPost = new HttpPost("http://localhost:3000/reset");
//    HttpResponse response = (HttpResponse) httpPost;
//  }

}
