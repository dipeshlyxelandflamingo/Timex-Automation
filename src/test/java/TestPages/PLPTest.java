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
        // HomePage object use karke Watches category me jao
        home = new HomePage(driver);
        home.goToWatchesCategory();        
        plp = new PLPPage(driver);
    }

    @Test(priority = 1)
    public void ClickOnShowFilterButton() throws Exception {
        plp.clickShowFilter();
    }

    @Test(priority = 2)
    public void ClickOnBandColorFilter() throws Exception {
        // Example: select Blue color with 14 products expected
        plp.selectBandColorFilter("Blue (14)", 14);
    }

   @Test(priority = 3)
    public void TC_03_DialColorFilter() throws Exception {
        // Example: select Pink color with 3 products expected
        plp.selectDialColorFilter("Pink (3)", 3);
    }

    

    @Test(priority = 4)
    public void ClickOnBandMaterialFilter() throws Exception {
        // Example: select Black material with 22 products expected
        plp.selectBandMaterialFilter("Black (22)", 22);
    }

    @Test(priority = 5)
    public void ClickOnFirstProduct() throws Exception {
        plp.clickOnFirstProduct();
    }
}
