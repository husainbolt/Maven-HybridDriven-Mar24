package testscripts;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import constants.ConstantFilePath;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import utilities.ExcelOperations;

@Epic("Login Page Feature")
public class LoginTest extends TestBase {

	@Test
	@Description("This test attempts to verify Login functionality.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Munira")
	@Story("Login Page")
	public void verifyLogin() {
		// TODO Auto-generated method stub

		loginPage.login("hbolty@gmail.com", "Pass=1234");
		boolean loginFlag = loginPage.isLoginSuccessfull();
		Assert.assertTrue(loginFlag);
	}

	@Test
	@Description("This test attempts to verify login error message functionality.")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Husain")
	@Story("Login Page")
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
	@Description("This test attempts to verify password error message functionality.")
    @Severity(SeverityLevel.MINOR)
    @Owner("Huzefa")
	@Story("Login Page")
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
	@Description("This test attempts to verify email error message functionality.")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Huzefa")
	@Story("Login Page")
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
	@Description("This test attempts to verify multiple user login functionality.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Hakim")
	@Story("Login Page")
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
