package TestPages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.BaseClass;
import Pages.HomePage;

public class HomeTest extends BaseClass {

	HomePage home;

	@BeforeClass
	public void setUpHomePage() {
		home = new HomePage(driver);
		SoftAssert sa = new SoftAssert();
	}

	@Test(priority = 1)
	public void ClickOnMarlinShopNow() throws Exception {

		SoftAssert sa = new SoftAssert();
		home.clickMarlinShopNowAndCloseTab();
		sa.assertTrue(false, "Failed to Click on MarlinShopNow Button");
		sa.assertAll();

	}

	@Test(priority = 2)
	public void ClickOnLatestReleaseProducts() {
		SoftAssert sa = new SoftAssert();
		home.clickLatestReleaseProducts();
		sa.assertTrue(false, "Failed to Click on Latest Release Products List");
		sa.assertAll();

	}

	@Test(priority = 3)
	public void clickOnPopularCategoryProducts() {
		SoftAssert sa = new SoftAssert();
		home.clickPopularCategory();
		sa.assertTrue(false, "Failed to Click on Popular Category Products List");
		sa.assertAll();

	}

	@Test(priority = 4)
	public void ClickOnkLeatherStrapWatchButton() {
		SoftAssert sa = new SoftAssert();
		home.clickLeatherStrapWatch();
		sa.assertTrue(false, "Failed to Click on Leather Strap Watch Button");
		sa.assertAll();

	}

	@Test(priority = 5)
	public void ClicOnkMoreToLoveSection() {
		SoftAssert sa = new SoftAssert();
		home.clickMoreToLoveSection();
		sa.assertTrue(false, "Failed to Click on More To Love Section");
		sa.assertAll();

	}

	@Test(priority = 6)
	public void ClicOnWatchesCategory() throws Exception {
		SoftAssert sa = new SoftAssert();
		home.goToWatchesCategory();
		sa.assertTrue(false, "Failed to Click on Watches Category");
		sa.assertAll();
	}
}
