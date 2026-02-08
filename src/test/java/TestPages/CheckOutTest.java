package TestPages;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseClass;
import Listeners.TestListener;
import Pages.CheckOutPage;

import Pages.MiniCartPage;
import Pages.PDPPage;


@Listeners(TestListener.class)
public class CheckOutTest extends BaseClass {

	private static final String PDP_URL = "https://shop.timexindia.com/products/tw000t310";

	PDPPage pdp;
	MiniCartPage miniCart;
	CheckOutPage checkout;

	@BeforeClass(alwaysRun = true)
	public void initFlow() {

		driver.get(PDP_URL);

		pdp = new PDPPage(driver);
		boolean added = pdp.addProductToCart();
		if (!added) {
			Assert.fail("❌ Add to cart failed on PDP. Checkout flow can't continue.");
		}

		miniCart = new MiniCartPage(driver);
		miniCart.failIfCartEmpty();

		miniCart.enterPincode("201306");
		miniCart.goToQuickCheckout();

		checkout = new CheckOutPage(driver);
	}

	@Test(priority = 1, groups = {"checkout"})
	public void VerifyCheckoutVisible() {
		try {
			checkout.verifyCheckoutVisible();
		} catch (Exception e) {
			Assert.fail("Failed to Check Checkout Visibility: " + e.getMessage(), e);
		}
	}

	@Test(priority = 2, groups = {"checkout"})
	public void VerifyCheckoutEnabled() {
		try {
			checkout.verifyCheckoutEnabled();
		} catch (Exception e) {
			Assert.fail("Failed to Enable Checkout: " + e.getMessage(), e);
		}
	}
}