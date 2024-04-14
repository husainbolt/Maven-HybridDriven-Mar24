package testscripts;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import base.ControlActions;
import io.qameta.allure.Step;
import pages.DashboardPage;
import pages.LoginPage;

public class TestBase {

	LoginPage loginPage;
	DashboardPage dashboardPage;
	static int count = 1;

	@BeforeMethod
	void start() {
		ControlActions.LaunchBrowser();
		loginPage = new LoginPage();
		dashboardPage = new DashboardPage();

	}

	@AfterMethod
	void teardown(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			ControlActions.takesScreenshot(result.getName() + "__" + count++);
			ControlActions.takesScreenshotwithAllure(result.getName() + "__" + count++);
		}
		ControlActions.closeBrowser();
	}

	@Step("Login with default credentials")
	public void login() {
		loginPage.login("hbolty@gmail.com", "Pass=1234");
		boolean loginFlag = loginPage.isLoginSuccessfull();
		Assert.assertTrue(loginFlag);
	}

}
