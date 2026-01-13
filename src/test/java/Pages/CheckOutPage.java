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
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		
		
	}

	// ✅ Verify Checkout Page Visible
	public void verifyCheckoutVisible() {

		// 🔹 GoKwik iframe me switch karo
		WebElement gokwikFrame = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@src,'gokwik')]")));

		driver.switchTo().frame(gokwikFrame);

		// 🔹 Checkout container verify
		WebElement checkoutContainer = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'gk-content')]")));

		Assert.assertTrue(checkoutContainer.isDisplayed(), "❌ Checkout page is NOT visible");

		System.out.println("✔ Checkout page is visible");
	}

	// ✅ Verify Mobile input enabled (strong validation)
	public void verifyCheckoutEnabled() {

		WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("phone-input")));

		Assert.assertTrue(phoneInput.isEnabled(), "❌ Checkout page not enabled");

		System.out.println("✔ Checkout page is enabled");
	}
}
