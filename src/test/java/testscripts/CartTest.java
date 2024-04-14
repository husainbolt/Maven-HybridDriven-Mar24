package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.CartPage;

@Epic("Cart Page Feature")
public class CartTest extends TestBase {

	@Test
	@Description("This test attempts to verify add to cart functionality.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Husain")
	@Story("Cart Page")
	public void verifyAddToCartFunctionality() {
		login();
		String productName = "ADIDAS ORIGINAL";
		System.out.println("Step - Click on Add to Cart for " + productName + " product to add it in Cart List");
		dashboardPage.addToCart(productName);
		dashboardPage.isAddedToCartMessageDisplayed();
		dashboardPage.waitForSpinnerToDisappear();
		// dashboardPage.waitForProductToastMessageToDisappear();

		System.out.println("STEP - Navigate to Cart Menu");
		CartPage cartPage = dashboardPage.clickOnCart();
		cartPage.waitForCartPageLoad();

		System.out.println("STEP - Get count of products added into cart");
		int countOfProducts = cartPage.getProductCountOnCartMenu();

		System.out.println("VERIFY - Count of product should be 1");
		Assert.assertEquals(countOfProducts, 1);

		System.out.println("VERIFY - Count of products in MyCart should be 1");
		countOfProducts = cartPage.getCountOfProductsInMyCart();
		Assert.assertEquals(countOfProducts, 1);

		System.out.println("VERIFY - Product is available in cart");
		boolean productVisibilityFlag = cartPage.isProductAvailableInMyCart(productName);
		Assert.assertTrue(productVisibilityFlag);
	}
	
	@Test
	@Description("This test attempts to verify multiple products added to cart functionality.")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Husain")
	@Story("Cart Page")
	public void verifyMultipleProductsAddToCartFunctionality() {
		login();
		String productName1 = "ADIDAS ORIGINAL";
		String productName2 = "ZARA COAT 3";
		Assert.fail("Error thrown purposely");
		System.out.println("Step - Click on Add to Cart");
		dashboardPage.addToCart(productName1);
		dashboardPage.waitForSpinnerToDisappear();
		// dashboardPage.waitForProductToastMessageToDisappear();
		dashboardPage.addToCart(productName2);
		dashboardPage.waitForSpinnerToDisappear();


		System.out.println("STEP - Navigate to Cart Menu");
		CartPage cartPage = dashboardPage.clickOnCart();
		cartPage.waitForCartPageLoad();

		System.out.println("STEP - Get count of products added into cart");
		int countOfProducts = cartPage.getProductCountOnCartMenu();

		System.out.println("VERIFY - Count of product should be 2");
		Assert.assertEquals(countOfProducts, 2);

		System.out.println("VERIFY - Count of products in MyCart should be 2");
		countOfProducts = cartPage.getCountOfProductsInMyCart();
		Assert.assertEquals(countOfProducts, 2);

		System.out.println("VERIFY - "+productName1+" is available in cart");
		boolean product1VisibilityFlag = cartPage.isProductAvailableInMyCart(productName1);
		Assert.assertTrue(product1VisibilityFlag);
		
		System.out.println("VERIFY - "+productName2+" is available in cart");
		boolean product2VisibilityFlag = cartPage.isProductAvailableInMyCart(productName2);
		Assert.assertTrue(product2VisibilityFlag);
	}
	
	@Test
	@Description("This test attempts to verify cart list after logout.")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Munira")
	@Story("Cart Page")
	void verifyCartListAfterLogout() {
		
		login();
		String productName = "ADIDAS ORIGINAL";
		System.out.println("Step - Click on Add to Cart for " + productName + " product to add it in Cart List");
		dashboardPage.addToCart(productName);
		dashboardPage.waitForSpinnerToDisappear();
		// dashboardPage.waitForProductToastMessageToDisappear();

		System.out.println("STEP - Navigate to Cart Menu");
		CartPage cartPage = dashboardPage.clickOnCart();
		cartPage.waitForCartPageLoad();

		System.out.println("STEP - Get count of products added into cart");
		int countOfProducts = cartPage.getProductCountOnCartMenu();

		System.out.println("VERIFY - Count of product should be 1");
		Assert.assertEquals(countOfProducts, 1);

		System.out.println("STEP - Click on Sign Out button");
		dashboardPage.clickOnLogoutButton();
		
		System.out.println("STEP - Log into the application");
		login();
		
		System.out.println("STEP - Navigate to Cart Menu");
		dashboardPage.clickOnCart();
		cartPage.waitForCartPageLoad();
		boolean isProductPresentInCart = cartPage.isNoProductInCartPresent();
		Assert.assertTrue(isProductPresentInCart);

		
	}

}
