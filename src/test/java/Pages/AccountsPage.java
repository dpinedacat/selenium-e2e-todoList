package Pages;

import static io.restassured.RestAssured.given;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountsPage {
  private WebDriver driver;

  @FindBy(xpath = "/html/body/pre")
  private List<WebElement> accounts;

  public void resetAccounts(){
    given().post("http://localhost:3000/reset");
  }

  public AccountsPage(WebDriver driver) {
    this.driver = driver;

    PageFactory.initElements(driver, this);
  }
}
