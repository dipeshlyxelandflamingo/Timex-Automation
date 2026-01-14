package TestPages;

import org.testng.Assert;
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
	    public void ClickOnMarlinShopNow() {

	        try {
	            home.clickMarlinShopNowAndCloseTab();
	        } catch (Exception e) {
	            Assert.fail("Failed to Click on MarlinShopNow Button");
	        }
	    }

	    @Test(priority = 2)
	    public void ClickOnLatestReleaseProducts() {

	        try {
	            home.clickLatestReleaseProducts();
	        } catch (Exception e) {
	            Assert.fail("Failed to Click on Latest Release Products List");
	        }
	    }

	    @Test(priority = 3)
	    public void clickOnPopularCategoryProducts() {

	        try {
	            home.clickPopularCategory();
	        } catch (Exception e) {
	            Assert.fail("Failed to Click on Popular Category Products List");
	        }
	    }

	    @Test(priority = 4)
	    public void ClickOnkLeatherStrapWatchButton() {

	        try {
	            home.clickLeatherStrapWatch();
	        } catch (Exception e) {
	            Assert.fail("Failed to Click on Leather Strap Watch Button");
	        }
	    }

	    @Test(priority = 5)
	    public void ClicOnkMoreToLoveSection() {

	        try {
	            home.clickMoreToLoveSection();
	        } catch (Exception e) {
	            Assert.fail("Failed to Click on More To Love Section");
	        }
	    }

	    @Test(priority = 6)
	    public void ClicOnWatchesCategory() {

	        try {
	            home.goToWatchesCategory();
	        } catch (Exception e) {
	            Assert.fail("Failed to Click on Watches Category");
	        }
	    }
	}