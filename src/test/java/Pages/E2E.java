package Pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class E2E {
	
	
	WebDriver driver;

    HomePage home;
    PLPPage plp;
    PDPPage pdp;
    MiniCartPage miniCart;
    CheckOutPage checkout;

    public E2E(WebDriver driver) {
        this.driver = driver;

        home = new HomePage(driver);
        plp = new PLPPage(driver);
        pdp = new PDPPage(driver);
        miniCart = new MiniCartPage(driver);
        checkout = new CheckOutPage(driver);
    }

    // ✅ ONE COMPLETE USER JOURNEY
    public void runCompleteE2EFlow() {

        // 1️⃣ Homepage → Watches Category (PLP)
        home.goToWatchesCategory();

        // 2️⃣ PLP → Open any product (0–4 safe retry)
        boolean productOpened = false;
        for (int i = 0; i < 5; i++) {
            try {
                plp.clickProducts(i);
                productOpened = true;
                System.out.println("✔ PLP → Opened product index: " + i);
                break;
            } catch (Exception e) {
                System.out.println("❌ PLP product failed at index: " + i);
            }
        }

        if (!productOpened) {
            Assert.fail("❌ E2E FAILED: No product opened from PLP");
        }

        // 3️⃣ PDP → Add to Cart
        boolean added = pdp.addProductToCart();
        if (!added) {
            Assert.fail("❌ E2E FAILED: Product not added to cart from PDP");
        }

        // 4️⃣ Mini Cart → Validate not empty + Pincode
        miniCart.enterPincode("201306");

        // 5️⃣ Mini Cart → Quick Checkout
        miniCart.goToQuickCheckout();

        // 6️⃣ Checkout → Verify page
        checkout.verifyCheckoutVisible();
        checkout.verifyCheckoutEnabled();

        System.out.println("🎉 E2E FLOW COMPLETED SUCCESSFULLY");
    }
}


