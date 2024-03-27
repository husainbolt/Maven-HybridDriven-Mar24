package testscripts;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import base.ControlActions;
import pages.DashboardPage;
import pages.LoginPage;

public class TestBase {

	LoginPage loginPage;
	DashboardPage dashboardPage;

	@BeforeMethod
	void start() {
		ControlActions.LaunchBrowser();
		loginPage = new LoginPage();
		dashboardPage = new DashboardPage();
		
	}

	@AfterMethod
	void teardown(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus())
			ControlActions.takesScreenshot(result.getName());
		ControlActions.closeBrowser();
	}
	
	public void login() {
		loginPage.login("hbolty@gmail.com", "Pass=1234");
		boolean loginFlag = loginPage.isLoginSuccessfull();
		Assert.assertTrue(loginFlag);
	}

}
