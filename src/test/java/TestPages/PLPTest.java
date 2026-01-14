package TestPages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.BaseClass;
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

		SoftAssert sa = new SoftAssert();
		plp.clickShowFilter();
		sa.assertTrue(false, "Failed to Click on close Filter Option");
		sa.assertAll();
	}

	@Test(priority = 2)
	public void ClickOnBandColorFilter() {
		SoftAssert sa = new SoftAssert();
		plp.selectBandColorFilter("Blue (14)", 14);
		sa.assertTrue(false, "Failed to Click on Band Color Filter Option");
		sa.assertAll();
	}

	@Test(priority = 3)
	public void TC_03_DialColorFilter() {
		SoftAssert sa = new SoftAssert();
		plp.selectDialColorFilter("Pink (3)", 3);
		sa.assertTrue(false, "Failed to Click on Dial Color Filter Option");
		sa.assertAll();
	}

	@Test(priority = 4)
	public void ClickOnBandMaterialFilter() {
		SoftAssert sa = new SoftAssert();
		plp.selectBandMaterialFilter("Black (22)", 22);
		sa.assertTrue(false, "Failed to Click on Band Material filter Option");
		sa.assertAll();
	}

	@Test(priority = 5)
	public void ClickOnFirstProduct() {
		SoftAssert sa = new SoftAssert();
		plp.clickOnFirstProduct();
		sa.assertTrue(false, "Failed to Click on First Product in List");
		sa.assertAll();
	}
}
