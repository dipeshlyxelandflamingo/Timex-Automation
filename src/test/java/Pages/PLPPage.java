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

public class PLPPage {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;

	public PLPPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		this.js = (JavascriptExecutor) driver;
	}

	// ---------------- SHOW FILTER ----------------
	public void clickShowFilter() {
		WebElement filterBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[@aria-label='Show Filter Hide Filter']")));
		js.executeScript("arguments[0].click();", filterBtn);
	}

	// ---------------- BAND COLOR FILTER ----------------
	public void selectBandColorFilter(String color, int expectedCount) {

		openFilter("Gender");
		openFilter("Price");

		WebElement filter = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[contains(@aria-label,'Filter by Band Color')]")));
		scrollAndClick(filter);

		WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//div[contains(@class,'usf-facet-values')]//button[normalize-space(@title)='" + color + "']")));
		scrollAndClick(option);

		scrollAndClick(filter);

		waitForProductsToLoad();
		validateProductCount(expectedCount);

		clearAllFilters();
	}

	// ---------------- DIAL COLOR FILTER ----------------
	public void selectDialColorFilter(String color, int expectedCount) {

		WebElement filter = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[contains(@aria-label,'Filter by Dial Color')]")));
		scrollAndClick(filter);

		WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//div[contains(@class,'usf-facet-values')]//button[normalize-space(@title)='" + color + "']")));
		scrollAndClick(option);

		scrollAndClick(filter);

		waitForProductsToLoad();
		validateProductCount(expectedCount);

		clearAllFilters();
	}

	// ---------------- BAND MATERIAL FILTER ----------------
	public void selectBandMaterialFilter(String material, int expectedCount) {

		WebElement filter = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[contains(@aria-label,'Filter by Band Material')]")));
		scrollAndClick(filter);

		WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
				"//div[contains(@class,'usf-facet-values')]//button[normalize-space(@title)='" + material + "']")));
		scrollAndClick(option);

		scrollAndClick(filter);

		waitForProductsToLoad();
		validateProductCount(expectedCount);

		clearAllFilters();
	}

	// ---------------- CLICK FIRST PRODUCT ----------------
	public void clickOnFirstProduct() {
		WebElement product = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//a[@href='/collections/mens/products/tw2t80700zv']")));
		scrollAndClick(product);
	}

	// ---------------- HELPER METHODS ----------------

	private void scrollAndClick(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		js.executeScript("arguments[0].click();", element);
	}

	private void waitForProductsToLoad() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".usf-loader")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".usf-loader")));
		} catch (Exception e) {
			// loader not present – ignore
		}
	}

	private void validateProductCount(int expectedCount) {
		WebElement summary = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//span[@class='usf-sr-summary' and contains(text(), 'Men’s Watches')]")));
		String summaryText = summary.getText();
		int actualCount = Integer.parseInt(summaryText.replaceAll("[^0-9]", ""));
		Assert.assertEquals(actualCount, expectedCount,
				"Expected product count does not match filtered result");
	}

	private void clearAllFilters() {
		WebElement clearBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("(//button[@class='usf-clear-all usf-btn'])[1]")));
		scrollAndClick(clearBtn);
		waitForProductsToLoad();
	}

	private void openFilter(String filterName) {
		WebElement filterHeader = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[contains(@aria-label,'Filter by " + filterName + "')]")));
		scrollAndClick(filterHeader);
	}
}