package Tests;

import Pages.LoginPage;
import Pages.SignupPage;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginPageTests {

  static WebDriver driver;

  @BeforeClass
  public static void BeforeMethod() throws IOException {
    System.setProperty("webdriver.chrome.driver", "C://ChromeDriver/chromedriver.exe");
    driver = new ChromeDriver();

    driver.navigate().to("http://localhost:3000/login");

  }

  /*
  Scenario: User enters valid information to login
    Given: I am at the login page
    And: I enter login information
    And submit my credentials
    When: I click “Log in”
    Then:  A "User is logged in" message is shown
    And: I’m brought to the home page
   */
  @Test //annotation allows the test runner know that this is a test
  public void validLogin() throws InterruptedException {
    LoginPage loginPage = new LoginPage(driver);
    SignupPage signupPage = new SignupPage(driver);

    //create new user first in signup to use account to log in
    driver.navigate().to("http://localhost:3000/signup");
    Assert.assertTrue("Not currently on signup page", signupPage.isSignupPageOpened());

    signupPage.createTestUser();
    signupPage.clickSignupButton();

    //go to login page and login new user
    //fill up input fields
    driver.navigate().to("http://localhost:3000/login");
    Assert.assertTrue("Not currently on login page", loginPage.isLoginPageOpened());

    loginPage.setEmail("email@example.com");
    loginPage.setPassword("abc123");

    //verify email input matches valid user
    Assert.assertEquals(loginPage.getEmail(), "email@example.com");
    loginPage.clickLoginButton();

    //added wait to make sure message is seen
    //verify correct message is shown
    Thread.sleep(1500);
    Assert.assertEquals(loginPage.getLoginMessage(), "User is logged in");

    //verify redirection to home page
    Assert.assertTrue("Did not redirect to home page", loginPage.isHomePageOpened());

//    loginPage.resetAccounts();

  }

  /*
  Scenario: User enters invalid information to login
    Given: I am at the login page
    And: I enter login information
    And submit my credentials
    When: I click “Log in”
    Then:  A “Could not sign up”message is shown
   */
  @Test //annotation allows the test runner know that this is a test
  public void invalidLogin() throws InterruptedException {
    LoginPage loginPage = new LoginPage(driver);
    driver.navigate().to("http://localhost:3000/login");
    Assert.assertTrue("Not currently on login page", loginPage.isLoginPageOpened());


    //fill up input fields
    loginPage.setEmail("email@example.com");
    //wrong password
    loginPage.setPassword("wrongPassword");

    //verify email input matches valid user
    Assert.assertEquals(loginPage.getEmail(), "email@example.com");
    loginPage.clickLoginButton();

    //added wait to make sure message is seen
    //verify correct error message is shown
    Thread.sleep(1500);
    Assert.assertEquals(loginPage.getErrorMessage(), "Wrong email or password");

    //verify redirection to back to login page
    Assert.assertTrue("Did not stay on login page", loginPage.isLoginPageOpened());

//    loginPage.resetAccounts();

  }

  @Test //annotation allows the test runner know that this is a test
  public void expectedFail() throws InterruptedException {
    LoginPage loginPage = new LoginPage(driver);
    driver.navigate().to("http://localhost:3000/login");
    Assert.assertTrue("Not currently on login page", loginPage.isLoginPageOpened());


    //fill up input fields
    loginPage.setEmail("emailDoesNotExist@example.com");
    //wrong password
    loginPage.setPassword("wrongPassword");

    loginPage.clickLoginButton();

    //added wait to make sure message is seen
    //verify correct error message is shown
    Thread.sleep(2500);
    Assert.assertEquals("User was not able to log in",loginPage.getLoginMessage(), "User is logged in");

    //verify redirection to back to login page
    Assert.assertTrue("Did not redirect to home page", loginPage.isHomePageOpened());

//    loginPage.resetAccounts();

  }

  @AfterClass
  public static void closeBrowser() {
    // quit the driver
    driver.close();
    driver.quit();
  }
}
