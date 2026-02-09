package TestPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseClass;
import Listeners.TestListener;
import Pages.HomePage;
import Pages.PDPPage;
import Pages.PLPPage;

@Listeners(TestListener.class)
@Test(groups = {"PDP"})
public class PDPTest extends BaseClass {

    PDPPage pdp;

    @BeforeClass(alwaysRun = true)
    public void init() {
        pdp = new PDPPage(driver);
        pdp.openPDP(); // ✅ always same PDP
    }

    @Test(priority = 1, groups = {"PDP"})
    public void TC_ClickOnRecommendationsProducts() {
        try {
            pdp.clickOnProductRecommendationsAndCloseTab();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Recommendations Products List", e);
        }
    }

    @Test(priority = 2, groups = {"PDP"})
    public void ClickOnAddToCartButton() {
        try {
            boolean added = pdp.addProductToCart();
            if (!added) {
                Assert.fail("❌ Add to cart failed on PDP (alert came OR qty not visible).");
            }
            System.out.println("✔ Add to cart success on same PDP product");
        } catch (Exception e) {
            Assert.fail("Failed to Click on Add To Cart Button: " + e.getMessage(), e);
        }
    }
}