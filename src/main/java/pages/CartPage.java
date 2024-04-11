package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.ControlActions;

public class CartPage extends ControlActions {

	@FindBy(xpath = "//button[@routerlink='/dashboard/cart']/label")
	WebElement myCartProductCount;

	@FindBy(xpath = "//div[@class='cartSection']")
	List<WebElement> listOfProductsOnCartMenu;
	
	@FindBy(xpath="//div[@class='cartSection']//h3")
	List<WebElement> listOfProductNames;

	@FindBy(xpath = "//button[@class='btn btn-danger']")
	List<WebElement> deleteButtonList;

	@FindBy(xpath = "//h1[text()='No Products in Your Cart !']")
	WebElement noProductInCartMessage;
	
	@FindBy(xpath = "//h1[text()='My Cart']")
	WebElement myCartHeader;

	public CartPage() {
		PageFactory.initElements(driver, this);
	}

	public int getCountOfProductsInMyCart() {
		return listOfProductsOnCartMenu.size();
	}

	public List<String> getListOfProductDetailsInMyCart() {
		return getElementTextList(listOfProductsOnCartMenu);
	}
	
	public List<String> getListOfProductNamessInMyCart() {
		return getElementTextList(listOfProductNames);
	}

	public boolean isProductAvailableInMyCart(String productName) {
		return getElementTextList(listOfProductNames).contains(productName);
	}

	public boolean isProductDisplayedAtEndOfList(String productName) {

		if (getCountOfProductsInMyCart() - 1 == getElementTextList(listOfProductsOnCartMenu).lastIndexOf(productName))
			return true;
		return false;
	}

	public String getProductPrice(String productName) {

		String locator = String.format(
				"//h3[text()='%s']/parent::div/following-sibling::div[@class='prodTotal cartSection']/p", productName);
		return getElementText("XPATH", locator, false);
	}

	public int getProductCountOnCartMenu() {
		String text = getElementText(myCartProductCount, true);
		System.out.println("Count text : " + text);
		return Integer.parseInt(text);
		
	}

	public List<String> getProductDetailsOnCartMenu(String productName) {

		List<String> productDetailList = new ArrayList<String>();
		String locator = String.format("//h3[text()='%s']/preceding-sibling::p", productName);
		productDetailList.add(getElementText("XPATH", locator, true));

		productDetailList.add(productName);

		locator = String.format("//h3[text()='%s']/following-sibling::p[1]", productName);
		productDetailList.add(getElementText("XPATH", locator, false).trim());

		locator = String.format("//h3[text()='%s']/following-sibling::p[2]", productName);
		productDetailList.add(getElementText("XPATH", locator, false).trim());

		productDetailList.add(getProductPrice(productName));

		return productDetailList;

	}

	public void clickOnBuyNowBtnOnCartMenu(String productName) {

		String locator = String.format(
				"//h3[text()='%s']/parent::div/following-sibling::div//button[@class='btn btn-primary']", productName);
		clickOnElement("XPATH", locator, true);

	}

	public void clickOnDeleteBtnOnCartMenu(String productName) {
		String locator = String.format(
				"//h3[text()='%s']/parent::div/following-sibling::div//button[@class='btn btn-danger']", productName);
		clickOnElement("XPATH", locator, true);
	}

	public void removeAllProductsFromCart() {

		for (WebElement e : deleteButtonList) {
			waitForClickabilityOfElementLocated(e).click();
		}

	}
	
	public void waitForCartPageLoad() {
		waitForVisibilityOfElementLocated(myCartHeader);
	}

	public boolean isNoProductInCartPresent() {
		return isElementDisplayed(noProductInCartMessage);
	}

}
