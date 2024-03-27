package testscripts;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.ControlActions;
import constants.ConstantFilePath;
import pages.LoginPage;
import utilities.ExcelOperations;

public class LoginTest extends TestBase {

	@Test
	public void verifyLogin() {
		// TODO Auto-generated method stub

		loginPage.login("hbolty@gmail.com", "Pass=1234");
		boolean loginFlag = loginPage.isLoginSuccessfull();
		Assert.assertTrue(loginFlag);
	}

	@Test
	public void veriryLoginErrorMessages() {
		System.out.println("Step - Click on Login button");
		loginPage.loginButtonClick();
		System.out.println("Step - Verify Email error message is displayed");
		boolean isEmailErrorFlag = loginPage.isEmailErrorMessageDisplayed();
		Assert.assertTrue(isEmailErrorFlag);
		System.out.println("Step - Verify Password error message is displayed");
		boolean isPasswordErrorFlag = loginPage.isEmailErrorMessageDisplayed();
		Assert.assertTrue(isPasswordErrorFlag);

	}

	@Test
	public void veriryPasswordErrorMessage() {
		System.out.println("Step - Enter Email address");
		loginPage.enterUserEmail("hbolty@gmail.com");
		System.out.println("Step - Click on Login button");
		loginPage.loginButtonClick();
		System.out.println("Step - Verify Email error message is not displayed");
		boolean isEmailErrorFlag = loginPage.isEmailErrorMessageDisplayed();
		Assert.assertFalse(isEmailErrorFlag);
		System.out.println("Step - Verify Password error message is displayed");
		boolean isPasswordErrorFlag = loginPage.isPasswordErrorMessageDisplayed();
		Assert.assertTrue(isPasswordErrorFlag);

	}

	@Test
	public void veriryEmailErrorMessage() {
		System.out.println("Step - Enter Password");
		loginPage.enterPassword("Pass=1234");
		System.out.println("Step - Click on Login button");
		loginPage.loginButtonClick();
		System.out.println("Step - Verify Email error message is displayed");
		boolean isEmailErrorFlag = loginPage.isEmailErrorMessageDisplayed();
		Assert.assertTrue(isEmailErrorFlag);
		System.out.println("Step - Verify Password error message is not displayed");
		boolean isPasswordErrorFlag = loginPage.isPasswordErrorMessageDisplayed();
		Assert.assertFalse(isPasswordErrorFlag);

	}

	@Test(dataProvider = "loginData")
	public void verifyMultiUserLogin(String uName, String password, String loginStatus) {
		System.out.println("Step - Enter login credentials");
		loginPage.login(uName, password);
		System.out.println("Step - Verify login status");
		String currentURL = "";
		if (loginStatus.equals("Pass")) {
			System.out.println("Verify - Login Successfully toast message was displayed");
			Assert.assertTrue(loginPage.isLoginSuccessfull(), "Login Successfully message was not displayed");
			currentURL = loginPage.getCurrentPageURL();

			System.out.println("Verify - Incorrect email or password. toast message was not displayed");
			Assert.assertFalse(loginPage.isLoginUnSuccessfull(), "Incorrect email or password. message was displayed");

			System.out.println("Verify - Application should be directed to the dashboard page");
			Assert.assertTrue(currentURL.endsWith("dashboard/dash"));
		} else if (loginStatus.equals("Fail")) {
			System.out.println("Verify - Incorrect email or password. toast message was displayed");
			Assert.assertTrue(loginPage.isLoginUnSuccessfull(),
					"Incorrect email or password. message was not displayed");
			currentURL = loginPage.getCurrentPageURL();

			System.out.println("Verify - Login Successfully toast message was not displayed");
			Assert.assertFalse(loginPage.isLoginSuccessfull(), "Login Successfully message was displayed");

			System.out.println("Verify - Application should remain on login page");
			Assert.assertTrue(currentURL.endsWith("auth/login"));
		}
	}

	@DataProvider(name = "loginData")
	public Object[][] getLoginData() throws IOException {
		return ExcelOperations.readExcel(ConstantFilePath.LOGIN_WORKBOOK_PATH, "Login");
	}

}
