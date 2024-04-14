package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import constants.ConstantFilePath;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import utilities.PropOperation;

public abstract class ControlActions {

	static protected WebDriver driver;
	private static PropOperation prop;
	private static WebDriverWait wait;

	static public void LaunchBrowser() {
		
		String browser = System.getProperty("browserName")==null?"chrome":System.getProperty("browserName");
		switch(browser){
		
		
		case "chrome":
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			prop = new PropOperation(ConstantFilePath.ENV_FILEPATH);
			driver = new ChromeDriver(options);
			wait = new WebDriverWait(driver, Duration.ofSeconds(ConstantFilePath.WAIT));
			driver.get(prop.getValue("url"));
			driver.manage().window().maximize();
			break;
		
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			WebDriverManager.edgedriver().setup();
			prop = new PropOperation(ConstantFilePath.ENV_FILEPATH);
			driver = new EdgeDriver(edgeOptions);
			wait = new WebDriverWait(driver, Duration.ofSeconds(ConstantFilePath.WAIT));
			driver.get(prop.getValue("url"));
			driver.manage().window().maximize();
			break;
		
		}
		

			}

	protected WebElement getElement(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement e = null;
		switch (locatorType.toUpperCase()) {

		case "XPATH":
			if (isWaitRequired)
				e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			else
				e = driver.findElement(By.xpath(locatorValue));
			break;
		case "CSS":
			if (isWaitRequired)
				e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
			else
				e = driver.findElement(By.cssSelector(locatorValue));
			break;
		case "ID":
			if (isWaitRequired)
				e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
			else
				e = driver.findElement(By.id(locatorValue));
			break;
		case "NAME":
			if (isWaitRequired)
				e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			else
				e = driver.findElement(By.name(locatorValue));
			break;
		case "LINKTEXT":
			if (isWaitRequired)
				e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
			else
				e = driver.findElement(By.linkText(locatorValue));
			break;
		case "PARTIALLINKTEXT":
			if (isWaitRequired)
				e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
			else
				e = driver.findElement(By.partialLinkText(locatorValue));
			break;
		case "CLASSNAME":
			if (isWaitRequired)
				e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
			else
				e = driver.findElement(By.className(locatorValue));
			break;
		case "TAGNAME":
			if (isWaitRequired)
				e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
			else
				e = driver.findElement(By.tagName(locatorValue));
			break;
		default:
			System.out.println("Incorrect locator type used");
		}
		return e;

	}

	protected WebElement waitForVisibilityOfElementLocated(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	protected WebElement waitForClickabilityOfElementLocated(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	protected void waitForElementToBeInvisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConstantFilePath.FAST_WAIT));
		System.out.println("Checkpoint 1");
		wait.until(ExpectedConditions.invisibilityOf(element));
		System.out.println("Checkpoint 2");
	}

	
	protected boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException se) {
			return false;
		}
	}

	protected boolean isElementDisplayedWithWait(WebElement element) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
		} catch (Exception se) {
			return false;
		}
	}

	protected boolean isElementDisplayedWithWait(WebElement element, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		try {
			return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
		} catch (Exception se) {
			return false;
		}
	}

	protected String getCurrenPagetURL() {
		return driver.getCurrentUrl();
	}
	
	
	public static void takesScreenshot(String fileName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(".//screeshots." + fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Attachment(type = "image/png", value = "Screenshot of Failed TC {0}")
	public static byte[] takesScreenshotwithAllure(String fileName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		return ts.getScreenshotAs(OutputType.BYTES);
		
	}


	protected List<String> getElementTextList(List<WebElement> elementList) {
		List<String> elementTextList = new ArrayList<String>();
		for (WebElement element : elementList)
			elementTextList.add(element.getText());
		return elementTextList;
	}

	protected String getElementText(String locatorType, String locatorValue, boolean isWaitRequired) {
		return getElement(locatorType, locatorValue, isWaitRequired).getText();
	}

	protected String getElementText(WebElement element, boolean isWaitRequired) {
		if (isWaitRequired)
			waitForVisibilityOfElementLocated(element);
		return element.getText();
	}

	protected void clickOnElement(String locatorType, String locatorValue, boolean isWaitRequired) {

		// WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		// e.click();
		clickOnElement(locatorType, locatorValue, isWaitRequired, false);
	}

	protected void clickOnElement(String locatorType, String locatorValue, boolean isWaitRequired,
			boolean IsWaitReuiredBeforeClick) {

		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		if (IsWaitReuiredBeforeClick)
			waitForClickabilityOfElementLocated(e);
		e.click();
	}

	protected void clickOnElement(WebElement element, boolean isWaitRequired) {

		wait.until(ExpectedConditions.elementToBeClickable(element)).click();

	}

	protected boolean isElementSelected(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		return e.isSelected();
	}

	protected boolean isElementSelected(WebElement element, boolean isWaitRequired) {
		if (isWaitRequired)
			if (wait.until(ExpectedConditions.elementToBeSelected(element)))
				return element.isSelected();
		return element.isSelected();
	}
	
	protected void checkPageReady(WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Thread.sleep(5000);
		for (int i = 0; i <= 40; i++) {
			Thread.sleep(1000);
			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}

	/*
	 * protected int getElementListSize(String locatorValue, int count,
	 * isWaitRequired) { if(isWaitRequired) return
	 * wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector(
	 * locatorValue), count)); WebElement e = getElement(locatorValue, locatorValue,
	 * false) }
	 */

	public static void closeBrowser() {
		driver.quit();
	}

}
