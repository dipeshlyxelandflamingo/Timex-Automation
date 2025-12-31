package TestPages;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BaseClass;
import Pages.HomePage;
import Pages.MiniCartPage;
import Pages.PDPPage;
import Pages.PLPPage;

public class MiniCartTest extends BaseClass {

	 HomePage home;
	    PLPPage plp;
	    PDPPage pdp;
	    MiniCartPage miniCart;

	    @BeforeClass
	    public void initFlow() throws Exception {

	        // Navigate to category
	        home = new HomePage(driver);
	        home.goToWatchesCategory();

	        // Select a product from PLP
	        plp = new PLPPage(driver);
	        plp.clickOnFirstProduct();

	        // Add product to cart from PDP
	        pdp = new PDPPage(driver, wait, act);
	        pdp.addProductToCart();

	        // Initialize MiniCartPage
	        miniCart = new MiniCartPage(driver);
	    }

	    @Test(priority = 1, description = "Increase product quantity in mini cart")
	    public void TC_01_QuantityIncrease() throws Exception {
	        try {
	            miniCart.increaseQuantity(3);
	        } catch (Exception e) {
	            System.err.println("❌ Quantity increase test failed: " + e.getMessage());
	            throw e;
	        }
	    }

	   

	    @Test(priority = 2, description = "Enter pincode in mini cart")
	    public void TC_03_EnterPincode() throws Exception  {
	        try {
	            miniCart.enterPincode("201306");
	        } catch (Exception e) {
	            System.err.println("❌ Enter pincode test failed: " + e.getMessage());
	            throw e;
	        }
	    }

	    @Test(priority = 3, description = "Proceed to quick checkout from mini cart")
	    public void TC_04_GoQuickCheckout() throws Exception {
	        try {
	            miniCart.goToQuickCheckout();
	        } catch (Exception e) {
	            System.err.println("❌ Quick checkout test failed: " + e.getMessage());
	            throw e;
	        }
	    }
	}