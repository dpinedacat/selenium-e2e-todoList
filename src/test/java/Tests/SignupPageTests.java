package Tests;

import Pages.SignupPage;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class SignupPageTests {

  static WebDriver driver;

  @BeforeClass
  public static void BeforeMethod() throws IOException {
    System.setProperty("webdriver.chrome.driver", "C://ChromeDriver/chromedriver.exe");
    driver = new ChromeDriver();
    driver.navigate().to("http://localhost:3000/signup");

  }

  /*
  Scenario: User enters valid information to sign up
    Given: I am at the sign up page
    And: I enter a valid format email and password
    When: I click “Sign up”
    Then:  A “User is logged in” message is shown
    And: I’m redirected to the home page
  */
  @Test
  public void validSignup() throws InterruptedException {
    SignupPage signupPage = new SignupPage(driver);

//    signupPage.resetAccounts();

    Assert.assertTrue("Not currently on signup page", signupPage.isSignupPageOpened());

    //create new user first in signup page
    signupPage.createTestUser2();
    Assert.assertEquals(signupPage.getEmail(), "email2@example.com");

    signupPage.clickSignupButton();

    //added wait to make sure message is seen
    //verify correct message is shown
    Thread.sleep(1500);
    Assert.assertEquals(signupPage.getLoginMessage(), "User is logged in");

    //verify redirection to home page
    Assert.assertTrue("Did not redirect to home page", signupPage.isHomePageOpened());

//    signupPage.resetAccounts();

  }

  /*
  Scenario: User enters existing user information to sign up
    Given: I am at the sign up page
    AND: I enter an existing users email
    When: I click “Sign up”
    Then:  A “Could not sign up”message is shown
    And: I’m still at the sign up page
   */
  @Test
  public void invalidSignup() throws InterruptedException {
    SignupPage signupPage = new SignupPage(driver);
//    signupPage.resetAccounts();

    //redirect to signup page
    driver.navigate().to("http://localhost:3000/signup");
    Assert.assertTrue("Not currently on signup page", signupPage.isSignupPageOpened());

    //initiate new valid user
    signupPage.createTestUser3();
    signupPage.clickSignupButton();

    //attempt to recreate same user
    driver.navigate().to("http://localhost:3000/signup");
    signupPage.setEmail("email3@example.com");
    signupPage.setPassword("abc123");

    //verify email input matches valid user
    Assert.assertEquals(signupPage.getEmail(), "email3@example.com");
    signupPage.clickSignupButton();

    //added wait to make sure message is seen
    //verify correct error message is shown
    Thread.sleep(1500);
    Assert.assertEquals(signupPage.getErrorMessage(), "Could not sign up");

    //verify redirection to back to signup page
    Assert.assertTrue("Did not stay on login page", signupPage.isSignupPageOpened());

//    signupPage.resetAccounts();

  }

  @AfterClass
  public static void closeBrowser() {
//
//    SignupPage signupPage = new SignupPage(driver);
//    signupPage.resetAccounts();

    // quit the driver
    driver.close();
    driver.quit();
  }
}
