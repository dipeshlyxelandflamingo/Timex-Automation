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
		home = new HomePage(driver);
		home.goToWatchesCategory();
		plp = new PLPPage(driver);
	}

	@Test(priority = 1)
	public void clickOnShowFilter() {
		try {
			plp.clickshowfilter();
		} catch (Exception e) {
			Assert.fail("Failed to Click on Show Filter Option:");
		}
	}

	@Test(priority = 2)
	public void closegenderandpriceFilter() {
		try {
			plp.closeGenderandpricefilter();
		} catch (Exception e) {
			Assert.fail("Failed to close/open Gender & Price filter:");
		}
	}

	@Test(priority = 3)
	public void ClickOnBandColorFilter() {
		try {
			plp.openandselectbandcolorfilter("Black", 55);// 54
		} catch (Exception e) {
			Assert.fail("Failed Band Color Filter: ");
		}
	}

	@Test(priority = 4)
	public void ClickOnDialColorFilter() {
		try {
			plp.openandselectDialcolorfilter("Beige", 3);// 3
		} catch (Exception e) {
			Assert.fail("Failed Dial Color Filter: ");
		}
	}

	@Test(priority = 5)
	public void ClickOnCaseDiameterFilter() {
		try {
			plp.openandselectCaseDiameterfilter("30 MM", 1);// 1
		} catch (Exception e) {
			Assert.fail("Failed Case Diameter Filter: ");
		}
	}

	@Test(priority = 6)
	public void ClickOnBandMaterialcolorFilter() {
		try {
			plp.openandselectbandmaterialfilter("Blue", 6);// 6
		} catch (Exception e) {
			Assert.fail("Failed Band Material Filter:");
		}
	}

	@Test(priority = 7)
	public void clickOnProducts() {
		 boolean opened = false;

		    for (int i = 0; i < 5; i++) {   // first 5 products try
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
		}
	}
