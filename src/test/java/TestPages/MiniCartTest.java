package TestPages;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseClass;
import Listeners.TestListener;
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
		//plp.clickOnFirstProduct();

		// Add product to cart from PDP
		pdp = new PDPPage(driver);
		pdp.addProductToCart();

		// Initialize MiniCartPage
		miniCart = new MiniCartPage(driver);
	}

	@Test(priority = 1, description = "Increase product quantity in mini cart")
    public void ProductQuantityIncrease() {

        try {
            miniCart.increaseQuantity(3);
        } catch (Exception e) {
            Assert.fail("Failed to Increase the Product Quantity");
        }
    }

    @Test(priority = 2, description = "Enter pincode in mini cart")
    public void EnterPincode() {

        try {
            miniCart.enterPincode("201306");
        } catch (Exception e) {
            Assert.fail("Failed to Enter Pincode");
        }
    }

    @Test(priority = 3, description = "Proceed to quick checkout from mini cart")
    public void ClickOnGoQuickCheckoutButton() {

        try {
            miniCart.goToQuickCheckout();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Checkout Button");
        }
    }
}