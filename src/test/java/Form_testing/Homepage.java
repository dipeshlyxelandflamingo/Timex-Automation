package Form_testing;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Base.BaseClass;

public class Homepage extends BaseClass {

	@Test(priority = 1)
	void TC_01_MarlinAutomatic_ShopNowButton() throws Throwable {

		js.executeScript("window.scrollBy(0, 1400)");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement ClickOnShopNowButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"(//a[@href='/products/timex-men-green-analogue-round-stainless-steel-dial-watch-tw2v44600u9'])[2]")));

		String parent = driver.getWindowHandle();
		act.keyDown(Keys.CONTROL).click(ClickOnShopNowButton).keyUp(Keys.CONTROL).perform();

		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(parent)) {
				driver.switchTo().window(tab);
				break;
			}
		}

		Thread.sleep(2000);
		driver.close();
		driver.switchTo().window(parent);
	}

	@Test(priority = 2)
	void TC_02_ClickOnLatestReleaseAndBestSellerProducts() throws Throwable {

		js.executeScript("window.scrollBy(0, 500)");

		List<WebElement> Product = driver.findElements(By.xpath("//div[@class='product-bx']"));

		for (WebElement Products : Product) {

			System.out.println(Products.getText());

			String parent = driver.getWindowHandle();
			act.keyDown(Keys.CONTROL).click(Products).keyUp(Keys.CONTROL).perform();

			for (String tab : driver.getWindowHandles()) {
				if (!tab.equals(parent)) {
					driver.switchTo().window(tab);
					break;
				}
			}

			Thread.sleep(2000);
			driver.close();
			driver.switchTo().window(parent);

			// Refresh Elements after coming back
			Product = driver.findElements(By.xpath("//div[@class='product-bx']"));
		}
	}

	@Test(priority = 3)
	void TC_03_ClickOnPopularCategory() throws Throwable {

		js.executeScript("window.scrollBy(0, 600)");

		WebElement BestSellerclick = driver.findElement(By.xpath("(//a[@href='/collections/offers'])[3]"));

		String parent = driver.getWindowHandle();
		act.keyDown(Keys.CONTROL).click(BestSellerclick).keyUp(Keys.CONTROL).perform();

		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(parent)) {
				driver.switchTo().window(tab);
				break;
			}
		}

		Thread.sleep(2000);
		driver.close();
		driver.switchTo().window(parent);
	}

	@Test(priority = 4)
	void TC_04_ClickOnLeatherStrapWatch() throws Throwable {

		js.executeScript("window.scrollBy(0, 600)");

		WebElement BestSellerclick = driver.findElement(By.xpath(
				"(//a[@href='/products/timex-men-burgundy-stainless-steel-tonneau-dial-analog-watch-tw2v25400u9'])[6]"));

		String parent = driver.getWindowHandle();
		act.keyDown(Keys.CONTROL).click(BestSellerclick).keyUp(Keys.CONTROL).perform();

		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(parent)) {
				driver.switchTo().window(tab);
				break;
			}
		}

		Thread.sleep(2000);
		driver.close();
		driver.switchTo().window(parent);
	}

	@Test(priority = 5)
	void TC_05_ClickOnMoretoLoveSection() throws Throwable {

		js.executeScript("window.scrollBy(0, 3500)");

		WebElement BestSellerclick = driver
				.findElement(By.xpath("(//a[@href='/collections/timex-x-jacquie-aiche'])[4]"));

		String parent = driver.getWindowHandle();
		act.keyDown(Keys.CONTROL).click(BestSellerclick).keyUp(Keys.CONTROL).perform();

		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(parent)) {
				driver.switchTo().window(tab);
				break;
			}
		}

		Thread.sleep(2000);
		driver.close();
		driver.switchTo().window(parent);

	}

	@Test(priority = 6)
	void TC_06_ClickOnWatchesCategoryPage() throws Throwable {

		// Scroll to top of page
		js.executeScript("window.scrollTo(0, 0)");
		Thread.sleep(2000);

		// Click on Watches Tab
		WebElement WatchesTab = driver.findElement(By.xpath("//a[@class='nav_link_timex']"));
		WatchesTab.click();
		Thread.sleep(2000);

		// Click on Shop All Button inside Watches Menu
		WebElement ShopAll = driver.findElement(By.xpath("//a[@href='/collections/mens']"));
		ShopAll.click();
		Thread.sleep(3000);

	}
}