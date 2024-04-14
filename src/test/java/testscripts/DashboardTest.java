package testscripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Dashboard Page Feature")
public class DashboardTest extends TestBase {


	@Test
	@Description("This test attempts to verify items list of side filter.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Husain")
	@Story("Dashboard Page")
	public void verifyItemListOfSideFilter() {
		login();
		List<String> expectedCategoryOptions = new ArrayList<String>(Arrays.asList("fashion", "electronics", "household"));
		List<String> expectedSubCategoryOptions = new ArrayList<String>(Arrays.asList("t-shirts", "shirts", "shoes", "mobiles", "laptops"));
		List<String> expectedSearchForOptions = new ArrayList<String>(Arrays.asList("men", "women"));
		
		System.out.println("Step - Verify Category Item List count");
		Assert.assertEquals(dashboardPage.getTotalItemsInlistOfCategories(), 3);
		
		System.out.println("Step - Verify Category Item List Text");
		Assert.assertEquals(dashboardPage.getTextOfItemsInlistOfCategories(), expectedCategoryOptions);		
		
		System.out.println("Step - Verify Sub Category Item List count");
		Assert.assertEquals(dashboardPage.getTotalItemsInlistOfSubCategories(), 5);
		
		System.out.println("Step - Verify Sub Category Item List Text");
		Assert.assertEquals(dashboardPage.getTextOfItemsInlistOfSubCategories(), expectedSubCategoryOptions);
		
		System.out.println("Step - Verify Search For Item List count");
		Assert.assertEquals(dashboardPage.getTotalItemsInlistOfSearchForItems(), 2);
		
		System.out.println("Step - Verify Sub Category Item List Text");
		Assert.assertEquals(dashboardPage.getTextOfItemsInlistOfSearchForCategories(), expectedSearchForOptions);

	}
	
	@Test
	@Description("This test attempts to verify filters on dashboard page.")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Munira")
	@Story("Dashboard Page")
	public void verifyFilterTest() throws InterruptedException {
		login();
		
		System.out.println("Step - User selects electronics option under Categories");
		dashboardPage.selectOptionItem("electronics");
		
		System.out.println("Verify - Option for electronics is selected");
		boolean isOptionSelected = dashboardPage.isOptionSelected("electronics");
		Assert.assertTrue(isOptionSelected);
		
		System.out.println("Verify - Options are visible as per selected filter");
		int itemCount = dashboardPage.getCountOfCards();
		Assert.assertEquals(itemCount, 1);
	}

}
