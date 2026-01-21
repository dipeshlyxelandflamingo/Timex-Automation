package TestPages;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseClass;
import Listeners.TestListener;
import Pages.HomePage;
import Pages.PLPPage;



public class PLPTest extends BaseClass {

	HomePage home;
	PLPPage plp;

	@BeforeClass
	public void init() throws Exception {
		// HomePage object use karke Watches category me jao
		home = new HomePage(driver);
		home.goToWatchesCategory();
		plp = new PLPPage(driver);
	}

	@Test(priority = 1)
    public void ClickOnShowFilterButton() {

        try {
            plp.clickShowFilter();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Show / Close Filter Option");
        }
    }

    @Test(priority = 2)
    public void ClickOnBandColorFilter() {

        try {
            plp.selectBandColorFilter("Blue (14)", 14);
        } catch (Exception e) {
            Assert.fail("Failed to Click on Band Color Filter Option");
        }
    }

    @Test(priority = 3)
    public void TC_03_DialColorFilter() {

        try {
            plp.selectDialColorFilter("Pink (3)", 3);
        } catch (Exception e) {
            Assert.fail("Failed to Click on Dial Color Filter Option");
        }
    }

    @Test(priority = 4)
    public void ClickOnBandMaterialFilter() {

        try {
            plp.selectBandMaterialFilter("Black (22)", 22);
        } catch (Exception e) {
            Assert.fail("Failed to Click on Band Material Filter Option");
        }
    }

    @Test(priority = 5)
    public void ClickOnFirstProduct() {

        try {
            plp.clickOnFirstProduct();
        } catch (Exception e) {
            Assert.fail("Failed to Click on First Product in Product List");
        }
    }
}