package TestPages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.BaseClass;
import Pages.HomePage;
import Pages.MiniCartPage;
import Pages.PDPPage;
import Pages.PLPPage;

public class MiniCartTest extends BaseClass {

	HomePage home;
	PLPPage plp;
	PDPPage pdp;
	MiniCartPage miniCart;

	@BeforeClass
	public void initFlow() throws Exception {

		// Navigate to category
		home = new HomePage(driver);
		home.goToWatchesCategory();

		// Select a product from PLP
		plp = new PLPPage(driver);
		plp.clickOnFirstProduct();

		// Add product to cart from PDP
		pdp = new PDPPage(driver);
		pdp.addProductToCart();

		// Initialize MiniCartPage
		miniCart = new MiniCartPage(driver);
	}

	@Test(priority = 1, description = "Increase product quantity in mini cart")
	public void ProductQuantityIncrease() throws Exception {
		SoftAssert sa = new SoftAssert();
		miniCart.increaseQuantity(3);
		sa.assertTrue(false, "Failed to Increase the Product Quantity");
		sa.assertAll();

	}

	@Test(priority = 2, description = "Enter pincode in mini cart")
	public void EnterPincode() throws Exception {
		SoftAssert sa = new SoftAssert();
		miniCart.enterPincode("201306");
		sa.assertTrue(false, "Failed to Enter Pincode");
		sa.assertAll();

	}

	@Test(priority = 3, description = "Proceed to quick checkout from mini cart")
	public void ClickOnGoQuickCheckoutButton() throws Exception {
		SoftAssert sa = new SoftAssert();
		miniCart.goToQuickCheckout();
		sa.assertTrue(false, "Failed to Click on Checkout Button");
		sa.assertAll();

	}
}