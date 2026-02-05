package TestPages;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseClass;
import Listeners.TestListener;
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
		//plp.clickOnFirstProduct();

		// PDP page actions
		pdp = new PDPPage(driver); // PDP ke methods ready
	}

	@Test(priority = 1)
	public void TC_ClickOnRecommendationsProducts() {

		try {
			pdp.clickOnProductRecommendationsAndCloseTab();
		} catch (Exception e) {
			Assert.fail("Failed to Click on Recommendations Products List");
		}
	}

	@Test(priority = 2)
	public void ClickOnAddToCartButton() {

		try {
			pdp.addProductToCart();
		} catch (Exception e) {
			Assert.fail("Failed to Click on Add To Cart Button");
		}
	}
}