package TestPages;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseClass;
import Listeners.TestListener;
import Pages.HomePage;
import Pages.PLPPage;

@Listeners(TestListener.class)
@Test(groups = {"PLP"})
public class PLPTest extends BaseClass {

	PLPPage plp;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		plp = new PLPPage(driver);

		// ✅ Direct open PLP (no dependency on HomeTest)
		plp.openPLP();
	}

	@Test(priority = 1, groups = { "PLP" })
	public void clickOnShowFilter() {
		try {
			plp.clickshowfilter();
		} catch (Exception e) {
			Assert.fail("Failed to Click on Show Filter Option", e);
		}
	}

	@Test(priority = 2, groups = { "PLP" })
	public void closegenderandpriceFilter() {
		try {
			plp.closeGenderandpricefilter();
		} catch (Exception e) {
			Assert.fail("Failed to close/open Gender & Price filter", e);
		}
	}

	@Test(priority = 3, groups = { "PLP" })
	public void ClickOnBandColorFilter() {
		try {
			plp.openandselectbandcolorfilter("Black", 54);
		} catch (Exception e) {
			Assert.fail("Failed Band Color Filter", e);
		}
	}

	@Test(priority = 4, groups = { "PLP" })
	public void ClickOnDialColorFilter() {
		try {
			plp.openandselectDialcolorfilter("Beige", 3);
		} catch (Exception e) {
			Assert.fail("Failed Dial Color Filter", e);
		}
	}

	@Test(priority = 5, groups = { "PLP" })
	public void ClickOnCaseDiameterFilter() {
		try {
			plp.openandselectCaseDiameterfilter("30 MM", 1);
		} catch (Exception e) {
			Assert.fail("Failed Case Diameter Filter", e);
		}
	}

	@Test(priority = 6, groups = { "PLP" })
	public void ClickOnBandMaterialcolorFilter() {
		try {
			// ⚠️ NOTE: tumhara method "bandmaterialfilter" me actual value "Blue" hai,
			// agar ye real site par band material nahi hai, to yaha value correct rakhna.
			plp.openandselectbandmaterialfilter("Blue", 6);
		} catch (Exception e) {
			Assert.fail("Failed Band Material Filter", e);
		}
	}

	@Test(priority = 7, groups = { "PLP" })
	public void clickOnProducts() {

		boolean opened = false;

		for (int i = 0; i < 5; i++) {
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