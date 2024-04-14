package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.ControlActions;
import io.qameta.allure.Step;

public class LoginPage extends ControlActions {

	@FindBy(id = "userEmail")
	WebElement userEmailElement;

	@FindBy(id = "userPassword")
	WebElement userPasswordElement;

	@FindBy(id = "login")
	WebElement loginButton;

	@FindBy(xpath = "//div[@aria-label='Login Successfully']")
	WebElement loginSuccessMessage;
	
	@FindBy(xpath = "//div[text()='*Email is required']")
	WebElement emailIsRequired;
	
	@FindBy(xpath = "//div[text()='*Password is required']")
	WebElement passwordIsRequired;
	
	@FindBy(xpath = "//div[@aria-label='Incorrect email or password.']")
	WebElement loginFailureMessage;

	public LoginPage() {
		PageFactory pf = new PageFactory();
		pf.initElements(driver, this);
	}
	
	@Step("User is able to login with user email as {0} and password as {1}")
	public LoginPage login(String email, String password) {
		System.out.println("Step 1 - Enter Email Address");
		// driver.findElement(By.id("userEmail")).sendKeys(email);
		enterUserEmail(email);

		System.out.println("Step 2 - Enter Password");
		// driver.findElement(By.id("userPassword")).sendKeys(password);
		enterPassword(password);

		System.out.println("Step 3 - Clicked on Login Button");
		// driver.findElement(By.id("login")).click();
		return loginButtonClick();
	}
	
	@Step("Enter Username as {0}")
	public void enterUserEmail(String email) {
		userEmailElement.sendKeys(email);
	}
	
	@Step("Enter Password as {0}")
	public void enterPassword(String password) {
		userPasswordElement.sendKeys(password);
	}
	
	@Step("Click on Login button")
	public LoginPage loginButtonClick() {
		loginButton.click();
		return new LoginPage();
	}
	
	@Step("Veriy Email is required message is displayed")
	public boolean isEmailErrorMessageDisplayed() {
		return isElementDisplayed(emailIsRequired);
	}
	
	@Step("Veriy Password is required message is displayed")
	public boolean isPasswordErrorMessageDisplayed() {
		return isElementDisplayed(passwordIsRequired);
	}

	@Step("Verify Login is successful")
	public boolean isLoginSuccessfull() {
		return isElementDisplayedWithWait(loginSuccessMessage, 5);
	}
	
	public boolean isLoginUnSuccessfull() {
		return isElementDisplayedWithWait(loginFailureMessage, 5);
	}
	
	public String getCurrentPageURL() {
		return super.getCurrenPagetURL();
	}
}
