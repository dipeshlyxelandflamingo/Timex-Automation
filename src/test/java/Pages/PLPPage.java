package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
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
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		this.js = (JavascriptExecutor) driver;
		this.actions = new Actions(driver);
	}

	public void clickshowfilter() {

		By filterBtn = By.xpath("//span[@class='collection-filters__button-spacing']");
		By filterPanel = By.xpath("//div[contains(@class,'usf-facets') and contains(@class,'usf-sr-filters')]");

		// 1) Wait till button exists + visible
		WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(filterBtn));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

		// 2) Click + wait panel (retry 2 times)
		for (int i = 0; i < 3; i++) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(btn));
				js.executeScript("arguments[0].click();", btn);

				// 3) Wait till side filter panel opens
				wait.until(ExpectedConditions.visibilityOfElementLocated(filterPanel));
				return;

			} catch (Exception e) {
				btn = wait.until(ExpectedConditions.visibilityOfElementLocated(filterBtn));
				js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
			}
		}

		Assert.fail("Filter panel did not open after clicking Show Filter button");
	}

	public void scrollAndClick(By locator) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
		js.executeScript("arguments[0].click();", element);
	}

	public void closeGenderandpricefilter() throws Exception {

		By closegenderfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[1]");
		scrollAndClick(closegenderfilter);

		Thread.sleep(800);
		By closepricefilter = By.xpath("(//div[@class='usf-title usf-no-select'])[2]");
		scrollAndClick(closepricefilter);

	}

	public void openandselectbandcolorfilter(String colorName, int expectedCount) throws Exception {

		By openandclosebandfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[3]");
		scrollAndClick(openandclosebandfilter);

		WebElement selectbandfilter = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(@class,'usf-facet-values--Swatch')]//button[.//span[normalize-space()='"
						+ colorName + "']]")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectbandfilter);
		js.executeScript("arguments[0].click();", selectbandfilter);

		waitForProductsToLoad();
		scrollAndClick(openandclosebandfilter);
		validateProductCount(expectedCount);
		ClickOnClearbutton();
		Thread.sleep(800);
	}

	public void openandselectDialcolorfilter(String colorName, int expectedCount) throws Exception {

		By openandclosedialcolorfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[4]");
		scrollAndClick(openandclosedialcolorfilter);

		WebElement selectbandfilter = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(@class,'usf-facet-values--Swatch')]//button[.//span[normalize-space()='"
						+ colorName + "']]")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectbandfilter);
		js.executeScript("arguments[0].click();", selectbandfilter);

		waitForProductsToLoad();
		scrollAndClick(openandclosedialcolorfilter);
		validateProductCount(expectedCount);
		ClickOnClearbutton();
		Thread.sleep(800);
	}

	public void openandselectCaseDiameterfilter(String casediameterName, int expectedCount) throws Exception {

		By openCaseDiameterfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[5]");
		scrollAndClick(openCaseDiameterfilter);

		WebElement selectbandfilter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//button[.//span[contains(@class,'usf-label') and normalize-space()='" + casediameterName + "']]")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectbandfilter);
		js.executeScript("arguments[0].click();", selectbandfilter);

		waitForProductsToLoad();
		By closeCaseDiameterfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[3]");
		scrollAndClick(closeCaseDiameterfilter);
		validateProductCount(expectedCount);
		ClickOnClearbutton();
		Thread.sleep(800);
	}

	public void openandselectbandmaterialfilter(String bandMaterial, int expectedCount) throws Exception {

		By openbandmaterialfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[6]");
		scrollAndClick(openbandmaterialfilter);

		WebElement selectbandfilter = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(@class,'usf-facet-values--Box')]//button[.//span[normalize-space()='"
						+ bandMaterial + "']]")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectbandfilter);
		js.executeScript("arguments[0].click();", selectbandfilter);

		waitForProductsToLoad();
		By closebandmaterialfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[5]");
		scrollAndClick(closebandmaterialfilter);
		validateProductCount(expectedCount);
		ClickOnClearbutton();
		Thread.sleep(800);
	}

	public void clickFirstProduct() throws Exception {

		Thread.sleep(800);
		WebElement selectbandfilter = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/collections/mens/products/tw000t310']")));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectbandfilter);
		js.executeScript("arguments[0].click();", selectbandfilter);

	}

	private void waitForProductsToLoad() {

		By summaryB = By.xpath("//span[contains(@class,'usf-sr-summary')]//b");
		WebElement summary = wait.until(ExpectedConditions.visibilityOfElementLocated(summaryB));
		String oldText = summary.getText().trim();

		By anySpinner = By.cssSelector(".usf-loader, .usf-loading, .loading, .spinner");

		try {
			wait.until(driver -> {
				try {
					if (!driver.findElements(anySpinner).isEmpty()) {
						for (WebElement sp : driver.findElements(anySpinner)) {
							if (sp.isDisplayed())
								return false;
						}
					}

					String newText = driver.findElement(summaryB).getText().trim();
					return !newText.equals(oldText);

				} catch (StaleElementReferenceException e) {
					return true;
				} catch (Exception e) {
					return false;
				}
			});
		} catch (Exception e) {
		}
	}

	private void validateProductCount(int expectedCount) {

		By summaryB = By.xpath("//span[contains(@class,'usf-sr-summary')]//b");
		WebElement summary = wait.until(ExpectedConditions.visibilityOfElementLocated(summaryB));

		String summaryText = summary.getText().trim();
		int actualCount = Integer.parseInt(summaryText.replaceAll("[^0-9]", ""));

		Assert.assertEquals(actualCount, expectedCount, "Expected product count does not match filtered result");
	}

	private void ClickOnClearbutton() {

		By clearBtn = By.xpath("(//button[contains(@class,'usf-clear-all')])[1]");
		WebElement clear = wait.until(ExpectedConditions.elementToBeClickable(clearBtn));

		js.executeScript("arguments[0].scrollIntoView({block:'center'});", clear);
		js.executeScript("arguments[0].click();", clear);
	}
}