package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PLPPage {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	Actions actions;

	public PLPPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.js = (JavascriptExecutor) driver;
		this.actions = new Actions(driver);
	}

	/* ===================== SHOW FILTER ===================== */

	public void clickShowFilter() {
		safeClick(By.xpath("//button[contains(@aria-label,'Show Filter')]"));
	}

	/* ===================== BAND COLOR FILTER ===================== */

	public void selectBandColorFilter(String color, int expectedCount) {

		openFilter("Band Color");

		safeClick(By.xpath("//button[@title='" + color + "']"));

		waitForProductsToLoad();
		validateProductCount(expectedCount);

		clearAllFilters();
	}

	/* ===================== DIAL COLOR FILTER ===================== */

	public void selectDialColorFilter(String color, int expectedCount) {

		openFilter("Dial Color");

		safeClick(By.xpath("//button[@title='" + color + "']"));

		waitForProductsToLoad();
		validateProductCount(expectedCount);

		clearAllFilters();
	}

	/* ===================== BAND MATERIAL FILTER ===================== */

	public void selectBandMaterialFilter(String material, int expectedCount) {

		openFilter("Band Material");

		safeClick(By.xpath("//button[@title='" + material + "']"));

		waitForProductsToLoad();
		validateProductCount(expectedCount);

		clearAllFilters();
	}

	/* ===================== CLICK FIRST PRODUCT ===================== */

	public void ClickonFirstProduct() {

		By ProductOpen = By.xpath("(//a[contains(@class,'prd-h-img')])[1]");

		WebElement ClickOnPro = wait.until(ExpectedConditions.elementToBeClickable(ProductOpen));

		SafeClick(ClickOnPro);

	}

	/* ============================================================ */
	/* ===================== HELPER METHODS ======================== */
	/* ============================================================ */
	
	private void SafeClick(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
		js.executeScript("arguments[0].click();", element);
	}

	// ✅ SAFE CLICK METHOD (No issues in Jenkins)
	private void safeClick(By locator) {

		for (int i = 0; i < 2; i++) {
			try {

				WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

				js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);

				try {
					element.click();
				} catch (Exception e) {
					actions.moveToElement(element).click().perform();
				}

				return;

			} catch (StaleElementReferenceException e) {
				System.out.println("Retry due to stale element...");
			}
		}

		throw new RuntimeException("Element not clickable: " + locator);
	}

	// ✅ Proper Loader Wait
	private void waitForProductsToLoad() {

		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".usf-loader, .usf-spinner")));
		} catch (Exception e) {
			System.out.println("Loader not visible, continue...");
		}
	}

	// ✅ Correct Product Count Validation
	private void validateProductCount(int expectedCount) {

		WebElement summary = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".usf-sr-summary")));

		String text = summary.getText();
		System.out.println("Summary Text: " + text);

		// Example: Showing 1–24 of 150 Men’s Watches
		String total = text.replaceAll(".*of\\s+(\\d+).*", "$1");

		int actualCount = Integer.parseInt(total);

		Assert.assertEquals(actualCount, expectedCount, "Filtered product count mismatch!");
	}

	// ✅ Clear All Filters Safely
	private void clearAllFilters() {
		safeClick(By.xpath("//button[contains(@class,'usf-clear-all')]"));
		waitForProductsToLoad();
	}

	// ✅ Open Any Filter
	private void openFilter(String filterName) {
		safeClick(By.xpath("//button[contains(@aria-label,'Filter by " + filterName + "')]"));
	}
}
