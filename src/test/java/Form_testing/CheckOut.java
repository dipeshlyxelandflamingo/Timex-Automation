package Form_testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseClass;

public class CheckOut extends BaseClass {

	@Test(priority = 1)
	public void TC_01_CheckOutEnableStatus() {
		WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

		// Wait until Order Summary text is visible
		WebElement orderSummary = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Order Summary')]")));

		// Check visibility
		Assert.assertTrue(orderSummary.isDisplayed(), "❌ Order Summary is NOT visible");
		System.out.println("✔ Order Summary text is visible");
	}
}
