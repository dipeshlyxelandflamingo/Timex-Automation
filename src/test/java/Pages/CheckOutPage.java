package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CheckOutPage {

	    WebDriver driver;
	    WebDriverWait wait;

	    public CheckOutPage(WebDriver driver) {
	        this.driver = driver;

	        boolean isLinux = System.getProperty("os.name", "").toLowerCase().contains("linux");
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(isLinux ? 35 : 20));
	    }

	    // ✅ Verify Checkout Page Visible
	    public void verifyCheckoutVisible() {

	        // SAME locators (no change)
	        By gokwikFrameBy = By.xpath("//iframe[contains(@src,'gokwik')]");
	        By checkoutContainerBy = By.xpath("//div[contains(@class,'gk-content')]");

	        // reset context (headless safe)
	        driver.switchTo().defaultContent();

	        try {
	            // ✅ wait + switch in one stable step
	            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(gokwikFrameBy));

	            WebElement checkoutContainer = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(checkoutContainerBy)
	            );

	            Assert.assertTrue(checkoutContainer.isDisplayed(), "❌ Checkout page is NOT visible");
	            System.out.println("✔ Checkout page is visible");

	        } finally {
	            // ✅ IMPORTANT: switch back always
	            driver.switchTo().defaultContent();
	        }
	    }

	    // ✅ Verify Mobile input enabled
	    public void verifyCheckoutEnabled() {

	        // SAME locators (no change)
	        By gokwikFrameBy = By.xpath("//iframe[contains(@src,'gokwik')]");
	        By phoneInputBy = By.id("phone-input");

	        driver.switchTo().defaultContent();

	        try {
	            // ✅ wait + switch stable
	            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(gokwikFrameBy));

	            WebElement phoneInput = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(phoneInputBy)
	            );

	            Assert.assertTrue(phoneInput.isEnabled(), "❌ Checkout page not enabled");
	            System.out.println("✔ Checkout page is enabled");

	        } finally {
	            // ✅ switch back always
	            driver.switchTo().defaultContent();
	        }
	    }
	}