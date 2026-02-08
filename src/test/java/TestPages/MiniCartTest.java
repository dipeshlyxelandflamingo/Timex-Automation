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

@Listeners(TestListener.class)
public class MiniCartTest extends BaseClass {

	PDPPage pdp;
    MiniCartPage miniCart;

    @BeforeClass (alwaysRun = true)
    public void initFlow() {

        // 1) Open PDP directly (use a stable PDP URL)
        driver.get("https://shop.timexindia.com/products/tw000t310");

        // 2) Add to cart from PDP
        pdp = new PDPPage(driver);

        boolean added = pdp.addProductToCart();
        if (!added) {
            Assert.fail("❌ Add to cart failed on PDP. MiniCart flow can't continue.");
        }

        // 3) Mini cart page init (NO openMiniCart)
        miniCart = new MiniCartPage(driver);

        // ✅ Important: fail early if mini cart is empty
        miniCart.failIfCartEmpty();
    }

    @Test(priority = 1, groups = {"minicart"})
    public void EnterPincode() {
        try {
            miniCart.enterPincode("201306");
        } catch (Exception e) {
            Assert.fail("Failed to Enter Pincode: " + e.getMessage(), e);
        }
    }

    @Test(priority = 2,groups = {"minicart"})
    public void ClickOnGoQuickCheckoutButton() {
        try {
            miniCart.goToQuickCheckout();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Checkout Button: " + e.getMessage(), e);
        }
    }
}
