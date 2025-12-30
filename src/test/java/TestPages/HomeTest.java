package TestPages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BaseClass;
import Pages.HomePage;

public class HomeTest extends BaseClass {

	HomePage home;

	@BeforeClass
	public void setUpHomePage() {
		home = new HomePage(driver);
	}

	@Test(priority = 1)
	public void clickOnMarlinShopNow() throws Exception {

		home.clickMarlinShopNowAndCloseTab();

	}

	@Test(priority = 2)
	public void clickOnLatestReleaseProducts() throws Exception {

		home.clickLatestReleaseProducts();

	}

	@Test(priority = 3)
	public void clickOnPopularCategoryProducts() throws Exception {

		HomePage home = new HomePage(driver);

		home.clickPopularCategory();

	}

	@Test(priority = 4)
	public void clickOnkLeatherStrapWatch() {

		home.clickLeatherStrapWatch();

	}

	@Test(priority = 5)
	public void clicOnkMoreToLoveSection() throws Exception {

		home.clickMoreToLoveSection();

	}

	@Test(priority = 6)
	public void clicOnWatchesCategory() throws Exception {

		home.goToWatchesCategory();
	}
}
