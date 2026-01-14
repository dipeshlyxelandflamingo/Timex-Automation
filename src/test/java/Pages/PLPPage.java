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
		WebElement filterBtn = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Show Filter Hide Filter']")));
		js.executeScript("arguments[0].click();", filterBtn);
	}

	// ---------------- BAND COLOR FILTER ----------------
	public void selectBandColorFilter(String color, int expectedCount) {
		// Open required filters
		openFilter("Gender");
		openFilter("Price");
		// Open Band Color filter
		WebElement filter = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[contains(@aria-label,'Filter by Band Color')]")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", filter);
		js.executeScript("arguments[0].click();", filter);

		// Select color option
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//div[contains(@class,'usf-facet-values')]//button[normalize-space(@title)='" + color + "']")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
		js.executeScript("arguments[0].click();", option);

		// Collapse filter
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", filter);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		js.executeScript("arguments[0].click();", filter);

		// Wait and validate
		waitForProductsToLoad();
		validateProductCount(expectedCount);

		// Clear filter
		WebElement clearBtn = driver.findElement(By.xpath("(//button[@class='usf-clear-all usf-btn'])[1]"));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", clearBtn);
		js.executeScript("arguments[0].click();", clearBtn);
		waitForProductsToLoad();
	}

	// ---------------- DIAL COLOR FILTER ----------------
	public void selectDialColorFilter(String color, int expectedCount) {
		WebElement filter = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[contains(@aria-label,'Filter by Dial Color')]")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", filter);
		js.executeScript("arguments[0].click();", filter);

		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//div[contains(@class,'usf-facet-values')]//button[normalize-space(@title)='" + color + "']")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
		js.executeScript("arguments[0].click();", option);

		// Collapse filter
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", filter);
		js.executeScript("arguments[0].click();", filter);

		waitForProductsToLoad();
		validateProductCount(expectedCount);

		WebElement clearBtn = driver.findElement(By.xpath("(//button[@class='usf-clear-all usf-btn'])[1]"));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", clearBtn);
		js.executeScript("arguments[0].click();", clearBtn);
		waitForProductsToLoad();
	}

	// ---------------- BAND MATERIAL FILTER ----------------
	public void selectBandMaterialFilter(String material, int expectedCount) {
		WebElement filter = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[contains(@aria-label,'Filter by Band Material')]")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", filter);
		js.executeScript("arguments[0].click();", filter);

		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//div[contains(@class,'usf-facet-values')]//button[normalize-space(@title)='" + material + "']")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
		js.executeScript("arguments[0].click();", option);

		// Collapse filter
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", filter);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		js.executeScript("arguments[0].click();", filter);

		waitForProductsToLoad();
		validateProductCount(expectedCount);

		WebElement clearBtn = driver.findElement(By.xpath("(//button[@class='usf-clear-all usf-btn'])[1]"));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", clearBtn);
		js.executeScript("arguments[0].click();", clearBtn);
		waitForProductsToLoad();
	}

	// ---------------- CLICK FIRST PRODUCT ----------------
	public void clickOnFirstProduct() {
		WebElement product = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//a[@href='/collections/mens/products/tw2t80700zv']")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", product);
		js.executeScript("arguments[0].click();", product);
	}

	// ---------------- HELPER METHODS ----------------
	private void waitForProductsToLoad() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".usf-loader")));
		} catch (Exception e) {
			// Ignore if loader not found
		}
	}

	private void validateProductCount(int expectedCount) {
		WebElement summary = driver
				.findElement(By.xpath("//span[@class='usf-sr-summary' and contains(text(), 'Men’s Watches')]"));
		String summaryText = summary.getText(); // e.g. "Men’s Watches (14)"
		int actualCount = Integer.parseInt(summaryText.replaceAll("[^0-9]", ""));
		Assert.assertEquals(actualCount, expectedCount, "Expected product count does not match filtered result");
	}

	// ---------------- OPEN FILTER ----------------
	private void openFilter(String filterName) {
		WebElement filterHeader = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[contains(@aria-label,'Filter by " + filterName + "')]")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", filterHeader);
		js.executeScript("arguments[0].click();", filterHeader);
	}
}
