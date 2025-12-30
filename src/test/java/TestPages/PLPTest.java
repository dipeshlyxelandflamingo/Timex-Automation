package TestPages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BaseClass;
import Pages.HomePage;
import Pages.PLPPage;

public class PLPTest extends BaseClass {

	HomePage home;
	PLPPage plp;

	@BeforeClass
	public void init() throws Exception {

		// HomePage object use karke last method call karo
		home = new HomePage(driver);
		// HomeTest ka last method: goToWatchesCategory()
		home.goToWatchesCategory();		
		plp = new PLPPage(driver);
	}

	@Test(priority = 1)
	public void TC_01_ClickOnShowFilterButton() throws Exception {
		plp.ClickShowFilter();
	}

	@Test(priority = 2)
	public void TC_02_BandColorFilter() throws Exception {
		plp.selectBandColorFilter("Blue (13)", 13);
	}

	@Test(priority = 3)
	public void TC_03_DialColorFilter() throws Exception {
		plp.selectDialColorFilter("Pink (3)", 3);
	}

	@Test(priority = 4)
	public void TC_04_CaseDiameterFilter() throws Exception {
		plp.selectCaseDiameterFilter("30 MM (2)", 2);
	}

	@Test(priority = 5)
	public void TC_05_BandMaterialFilter() throws Exception {
		plp.selectBandMaterialFilter("Black (22)", 22);
	}

	@Test(priority = 6)
	public void TC_06_ClickOnProduct() throws Exception {
		plp.clickOnProduct();
	}

}