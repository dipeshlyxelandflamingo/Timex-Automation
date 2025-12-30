package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PDPPage {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	Actions act;

	public PDPPage(WebDriver driver, WebDriverWait wait, Actions act) {
		this.driver = driver;
		this.wait = wait;
		this.act = act;
		this.js = (JavascriptExecutor) driver;
	}

	// ---------------- Product Recommendation ----------------

	public void clickOnProductRecommendationsAndCloseTab() throws Exception {

		js.executeScript("window.scrollBy(0, 6500)");
		Thread.sleep(2000);

		List<WebElement> products = driver.findElements(By.xpath("//form[@class='home-cat-iteam ']"));

		for (WebElement product : products) {

			String parent = driver.getWindowHandle();

			act.keyDown(Keys.CONTROL).click(product).keyUp(Keys.CONTROL).perform();

			for (String tab : driver.getWindowHandles()) {
				if (!tab.equals(parent)) {
					driver.switchTo().window(tab);
					break;
				}
			}

			Thread.sleep(2000);
			driver.close();
			Thread.sleep(1000);
			driver.switchTo().window(parent);
		}
	}

	public void addProductToCart() throws Exception {
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0, 1000)");
		Thread.sleep(2000);
		WebElement addToCartBtn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button button--action product-details__sticky-atc-add-btn']")));

		addToCartBtn.click();
		System.out.println("✔ Product added to cart");
	}
}
