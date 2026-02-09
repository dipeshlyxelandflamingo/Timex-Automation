package TestPages;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseClass;
import Pages.HomePage;
import Listeners.TestListener;


@Listeners(TestListener.class)
@Test(groups = {"Homepage"})
public class HomeTest extends BaseClass {

    HomePage home;

    @BeforeClass(alwaysRun = true)
    public void setUpHomePage() {
        home = new HomePage(driver);
    }

    @Test(priority = 1, groups = {"Homepage"})
    public void ClickOnMarlinShopNow() {
        try {
            home.clickMarlinShopNowAndCloseTab();
        } catch (Exception e) {
            Assert.fail("Failed to Click on MarlinShopNow Button", e);
        }
    }

    @Test(priority = 2, groups = {"Homepage"})
    public void ClickOnLatestReleaseProducts() {
        try {
            home.clickLatestReleaseProducts();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Latest Release Products List", e);
        }
    }

    @Test(priority = 3, groups = {"Homepage"})
    public void clickOnPopularCategoryProducts() {
        try {
            home.clickPopularCategory();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Popular Category Products List", e);
        }
    }

    @Test(priority = 4, groups = {"Homepage"})
    public void ClickOnLeatherStrapWatchButton() {
        try {
            home.clickLeatherStrapWatch();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Leather Strap Watch Button", e);
        }
    }

    @Test(priority = 5, groups = {"Homepage"})
    public void ClickOnMoreToLoveSection() {
        try {
            home.clickMoreToLoveSection();
        } catch (Exception e) {
            Assert.fail("Failed to Click on More To Love Section", e);
        }
    }

    @Test(priority = 6, groups = {"Homepage"})
    public void ClickOnWatchesCategory() {
        try {
            home.goToWatchesCategory();
        } catch (Exception e) {
            Assert.fail("Failed to Click on Watches Category", e);
        }
    }
}