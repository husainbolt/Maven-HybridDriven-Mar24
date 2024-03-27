package pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.ControlActions;

public class DashboardPage extends ControlActions {

	@FindBy(xpath = "//section[@id='sidebar']//h6[text()='Categories']/following-sibling::div[not(@id)]")
	List<WebElement> listOfCategories;

	@FindBy(xpath = "//section[@id='sidebar']//h6[text()='Sub Categories']/following-sibling::div[not(@id)]")
	List<WebElement> listOfSubCategories;

	@FindBy(xpath = "//section[@id='sidebar']//h6[text()='Search For']/following-sibling::div[not(@id)]")
	List<WebElement> listOfSearchForItems;
	
	@FindBy(css = "div.card")
	List<WebElement> listOfCards;
	int totalCount = 0;
	
	@FindBy(xpath="//button[@routerlink='/dashboard/cart']")
	WebElement cartButton;
	
	@FindBy(xpath="//div[contains(@class,'la-ball-scale-multiple ng-star-inserted')]")
	WebElement spinner;
	
	@FindBy(xpath = "//div[@aria-label='Product Added To Cart']")
	WebElement productAddedtoCartToastMessage;
	
	@FindBy(xpath = "//button[@class=\"btn btn-custom\" and contains(text(), 'Sign Out')]")
	WebElement signoutBtn;
	
	public DashboardPage() {
		PageFactory pf = new PageFactory();
		pf.initElements(driver, this);
	}

	public int getTotalItemsInlistOfCategories() {
		return listOfCategories.size();
	}

	public List<String> getTextOfItemsInlistOfCategories() {

		return getElementTextList(listOfCategories);
	}

	public int getTotalItemsInlistOfSubCategories() {
		return listOfSubCategories.size();
	}

	public List<String> getTextOfItemsInlistOfSubCategories() {

		return getElementTextList(listOfSubCategories);
	}

	public int getTotalItemsInlistOfSearchForItems() {
		return listOfSearchForItems.size();
	}

	public List<String> getTextOfItemsInlistOfSearchForCategories() {

		return getElementTextList(listOfSearchForItems);
	}
	
	public void selectOptionItem(String itemName) {
		totalCount = listOfCards.size();
		String locatorValue = String .format("//section[@id='sidebar']//label[text()='%s']/preceding-sibling::input", itemName);
		clickOnElement("XPATH", locatorValue, true);
	}
	
	public int getCountOfCards() throws InterruptedException {
		/*
		 * String locatorValue = "div.card"; if(listOfCards.size()<totalCount) return
		 * listOfCards.size(); return getElementListSize(locatorValue, true);
		 */
		Thread.sleep(2000);
		return listOfCards.size();
	}
	
	public boolean isOptionSelected(String itemName) {
		String locatorValue = String .format("//section[@id='sidebar']//label[text()='%s']/preceding-sibling::input", itemName);
		return isElementSelected("XPATH", locatorValue, false);
	}
	
	public void addToCart(String productName) {
		productName = productName.toLowerCase();
		String locator = String.format("//b[text()='%s']/ancestor::div/button[contains(text(),'Add To Cart')]", productName.toUpperCase());
		clickOnElement("XPATH", locator, true);
	}
	
	public void waitForSpinnerToDisappear() {
		waitForVisibilityOfElementLocated(spinner);
		waitForElementToBeInvisible(spinner);
	}
	
	public void waitForProductToastMessageToDisappear() {
		waitForVisibilityOfElementLocated(productAddedtoCartToastMessage);
		waitForElementToBeInvisible(productAddedtoCartToastMessage);
	}
	
	public CartPage clickOnCart() {
		clickOnElement(cartButton, true);
		return new CartPage();
	}
	
	public void clickOnLogoutButton() {
		clickOnElement(signoutBtn, false);
	}

}
