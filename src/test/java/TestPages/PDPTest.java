package TestPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

    // ✅ same locator as your PDP (no change)
    private final By addToCartBtnBy = By.xpath("//button[@class='add-tocart-btn']");

    @BeforeClass
    public void init() throws Exception {

        home = new HomePage(driver);
        plp = new PLPPage(driver);
        pdp = new PDPPage(driver);

        // Go to Watches -> PLP
        home.goToWatchesCategory();

        // ✅ Open any product (0-4) which lands on PDP successfully
        boolean opened = false;

        for (int i = 0; i < 5; i++) {
            try {
                plp.clickProducts(i);  // <-- your PLP method (index based)

                // verify we are on PDP (add to cart visible)
                new WebDriverWait(driver, Duration.ofSeconds(20))
                        .until(ExpectedConditions.visibilityOfElementLocated(addToCartBtnBy));

                System.out.println("✔ Opened PDP using product index: " + i);
                opened = true;
                break;

            } catch (Exception e) {
                System.out.println("❌ Failed to open PDP for product index: " + i + " | " + e.getMessage());

                // try to recover: go back to PLP
                try { driver.navigate().back(); } catch (Exception ignored) {}
            }
        }

        if (!opened) {
            Assert.fail("❌ Failed to open any product PDP from PLP (0-4)");
        }
    }

    @Test(priority = 1)
    public void TC_ClickOnRecommendationsProducts() {
        try {
            pdp.clickOnProductRecommendationsAndCloseTab();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Recommendations Products List", e);
        }
    }

    @Test(priority = 2)
    public void ClickOnAddToCartButton() {

        boolean added = false;

        // ✅ If current PDP product fails, go back to PLP and try next products
        for (int i = 0; i < 5; i++) {
            try {
                // try add to cart (IMPORTANT: your PDP should return boolean now)
                boolean ok = pdp.addProductToCart();

                if (ok) {
                    added = true;
                    System.out.println("✔ Add to cart success on attempt index: " + i);
                    break;
                } else {
                    System.out.println("❌ Add to cart failed (alert/qty not visible). Trying next product...");
                }

            } catch (Exception e) {
                System.out.println("❌ Exception during add to cart: " + e.getMessage());
            }

            // go back to PLP and open next product
            try {
                driver.navigate().back();
            } catch (Exception ignored) {}

            try {
                plp.clickProducts(i); // try opening next product
                new WebDriverWait(driver, Duration.ofSeconds(20))
                        .until(ExpectedConditions.visibilityOfElementLocated(addToCartBtnBy));
            } catch (Exception e) {
                System.out.println("❌ Failed to open next product from PLP: " + i + " | " + e.getMessage());
            }
        }

        if (!added) {
            Assert.fail("❌ Failed to add any product to cart from PDP after trying 0-4 products");
        }
    }
}