package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	Actions act;

	// ✅ Proper constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		this.js = (JavascriptExecutor) driver;
		this.act = new Actions(driver);
	}

	

	/* ---------------- TC_02 ---------------- */

	public void clickMarlinShopNowAndCloseTab() throws Exception {

		js.executeScript("window.scrollBy(0,1400)");

		WebElement shopNowBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"(//a[@href='/products/timex-men-green-analogue-round-stainless-steel-dial-watch-tw2v44600u9'])[2]")));

		String parent = driver.getWindowHandle();

		act.keyDown(Keys.CONTROL).click(shopNowBtn).keyUp(Keys.CONTROL).perform();
		Thread.sleep(1000);
		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(parent)) {
				driver.switchTo().window(tab);
				break;
			}
		}

		driver.close();
		driver.switchTo().window(parent);
	}

	/* ---------------- TC_03 ---------------- */

	public void clickLatestReleaseProducts() throws Exception {

		js.executeScript("window.scrollBy(0,500)");

		List<WebElement> products = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='product-bx']")));

		for (WebElement product : products) {
			String parent = driver.getWindowHandle();

			act.keyDown(Keys.CONTROL).click(product).keyUp(Keys.CONTROL).perform();
			Thread.sleep(1000);
			for (String tab : driver.getWindowHandles()) {
				if (!tab.equals(parent)) {
					driver.switchTo().window(tab);
					break;
				}
			}

			driver.close();
			driver.switchTo().window(parent);
		}
	}

	/* ---------------- TC_04 ---------------- */

	public void clickPopularCategory() throws Exception {

		js.executeScript("window.scrollBy(0,600)");

		WebElement category = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@href='/collections/offers'])[3]")));

		String parent = driver.getWindowHandle();

		act.keyDown(Keys.CONTROL).click(category).keyUp(Keys.CONTROL).perform();
		Thread.sleep(1000);
		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(parent)) {
				driver.switchTo().window(tab);
				break;
			}
		}

		driver.close();
		driver.switchTo().window(parent);
	}

	/* ---------------- TC_05 ---------------- */

	public void clickLeatherStrapWatch() {

		js.executeScript("window.scrollBy(0,600)");

		WebElement product = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"(//a[@href='/products/timex-continental-black-round-dial-analog-mens-watch-tw2y25000uj'])[2]")));

		String parent = driver.getWindowHandle();

		act.keyDown(Keys.CONTROL).click(product).keyUp(Keys.CONTROL).perform();

		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(parent)) {
				driver.switchTo().window(tab);
				break;
			}
		}

		driver.close();
		driver.switchTo().window(parent);
	}

	/* ---------------- TC_06 ---------------- */

	public void clickMoreToLoveSection() throws Exception {

		js.executeScript("window.scrollBy(0,3500)");

		WebElement moreSection = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//a[@href='/collections/timex-x-jacquie-aiche'])[4]")));

		String parent = driver.getWindowHandle();

		act.keyDown(Keys.CONTROL).click(moreSection).keyUp(Keys.CONTROL).perform();
		Thread.sleep(1000);
		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(parent)) {
				driver.switchTo().window(tab);
				break;
			}
		}

		driver.close();
		driver.switchTo().window(parent);
		
	}

	/* ---------------- TC_07 ---------------- */

	public void goToWatchesCategory() throws Exception {
		Thread.sleep(2000);
		js.executeScript("window.scrollTo(0,0)");

		WebElement watchesTab = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='nav_link_timex']")));
		watchesTab.click();
		Thread.sleep(1000);
		WebElement shopAll = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/collections/mens']")));
		shopAll.click();
		Thread.sleep(1000);
	}

	/* ---------------- COMMON ---------------- */

}
