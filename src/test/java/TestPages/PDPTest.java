package TestPages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
		plp.clickOnProduct(); 

		// PDP page actions
		pdp = new PDPPage(driver, wait, act); // PDP ke methods ready
	}

	@Test(priority = 1)
	public void TC_01_clickOnProductRecommendations() throws Exception {
		pdp.clickOnProductRecommendationsAndCloseTab();
	}

	@Test(priority = 2)
	public void TC_02_addProductToCartProduct() throws Exception {
		pdp.addProductToCart();
	}
}