package TestPages;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseClass;
import Listeners.TestListener;
import Pages.CheckOutPage;
import Pages.HomePage;
import Pages.MiniCartPage;
import Pages.PDPPage;
import Pages.PLPPage;

@Listeners(TestListener.class)
public class CheckOutTest extends BaseClass {

	HomePage home;
	PLPPage plp;
	PDPPage pdp;
	MiniCartPage miniCart;
	CheckOutPage checkout;

	@BeforeClass
	public void initFlow() throws Exception {

		home = new HomePage(driver);
		home.goToWatchesCategory(); // Homepage last step

		plp = new PLPPage(driver);
		boolean opened = false;

		for (int i = 0; i < 5; i++) {
		    try {
		        plp.clickProducts(i);   // ✅ your new method
		        opened = true;
		        System.out.println("✔ Opened product index: " + i);
		        break;
		    } catch (Exception e) {
		        System.out.println("❌ Failed to open product index: " + i + " | " + e.getMessage());
		    }
		}

		if (!opened) {
		    Assert.fail("Failed to open any product from PLP (0-4)");
		}


		pdp = new PDPPage(driver);
		pdp.addProductToCart(); // PDP last step → MiniCart opens

		miniCart = new MiniCartPage(driver);
		miniCart.enterPincode("201306");
		miniCart.goToQuickCheckout();

		checkout = new CheckOutPage(driver);
	}

	@Test(priority = 1)
	public void VerifyCheckoutVisible() {

		try {
			checkout.verifyCheckoutVisible();
		} catch (Exception e) {
			Assert.fail("Failed to Check Checkout Visibility" + e.getMessage());
		}
	}

	@Test(priority = 2, dependsOnMethods = { "VerifyCheckoutVisible" })
	public void VerifyCheckoutEnabled() {

		try {
			checkout.verifyCheckoutEnabled();
		} catch (Exception e) {
			Assert.fail("Failed to Enable Checkout" + e.getMessage());
		}
	}
}
