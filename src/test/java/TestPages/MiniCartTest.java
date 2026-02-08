package TestPages;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

        home = new HomePage(driver);
        home.goToWatchesCategory();

        plp = new PLPPage(driver);

        boolean opened = false;
        for (int i = 0; i < 5; i++) {
            try {
                plp.clickProducts(i);
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
        pdp.addProductToCart();

        miniCart = new MiniCartPage(driver);
        miniCart.waitForMiniCartVisible();
        Assert.assertFalse(miniCart.isCartEmpty(), "❌ Product not added, mini cart empty");
    }

    @Test(priority = 1, description = "Enter pincode in mini cart")
    public void EnterPincode() {
        try {
            miniCart.enterPincode("201306");
        } catch (Exception e) {
            Assert.fail("Failed to Enter Pincode: " + e.getMessage(), e);
        }
    }

    @Test(priority = 2, description = "Proceed to quick checkout from mini cart")
    public void ClickOnGoQuickCheckoutButton() {
        try {
            miniCart.goToQuickCheckout();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Checkout Button: " + e.getMessage(), e);
        }
    }
}