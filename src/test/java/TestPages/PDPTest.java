package TestPages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.BaseClass;
import Pages.HomePage;
import Pages.PDPPage;
import Pages.PLPPage;

public class PDPTest extends BaseClass {

	HomePage home;
	PLPPage plp;
	PDPPage pdp;

	@BeforeClass
	public void init() throws Exception {
		// Home page actions
		home = new HomePage(driver);
		home.goToWatchesCategory();

		// PLP page actions
		plp = new PLPPage(driver);
		plp.clickOnFirstProduct(); 

		// PDP page actions
		pdp = new PDPPage(driver); // PDP ke methods ready
	}

	@Test(priority = 1)
	public void TC_ClickOnRecommendationsProducts() throws Exception {
		SoftAssert sa = new SoftAssert();
		pdp.clickOnProductRecommendationsAndCloseTab();
		sa.assertTrue(false, "Failed to Click on Recommendations Products List");
		sa.assertAll();
	}

	@Test(priority = 2)
	public void ClickOnAddToCartButton() throws Exception {
		SoftAssert sa = new SoftAssert();
		pdp.addProductToCart();
		sa.assertTrue(false, "Failed to Click on Add To Cart Button");
		sa.assertAll();
	}
}