package Pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PDPPage {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;

	public PDPPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		this.js = (JavascriptExecutor) driver;
	}

	/* ================= Helper ================= */

	private void openInNewTabAndClose(WebElement element) {
		String parent = driver.getWindowHandle();

		String url = element.getAttribute("href");
		js.executeScript("window.open(arguments[0], '_blank');", url);

		Set<String> windows = driver.getWindowHandles();
		for (String win : windows) {
			if (!win.equals(parent)) {
				driver.switchTo().window(win);
				break;
			}
		}

		driver.close();
		driver.switchTo().window(parent);
	}

	private void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
	}

	/* ---------------- Product Recommendation ---------------- */

	public void clickOnProductRecommendationsAndCloseTab() {

		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		List<WebElement> products = wait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//form[@class='home-cat-iteam ']")));

		for (WebElement product : products) {
			scrollToElement(product);
			wait.until(ExpectedConditions.elementToBeClickable(product));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			openInNewTabAndClose(product);
		}
	}

	/* ---------------- Add To Cart ---------------- */

	public void addProductToCart() {

		try

		{
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		js.executeScript("window.scrollBy(0, 1000)");

		try

		{
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WebElement addToCartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//button[@class='button button--action product-details__sticky-atc-add-btn']")));

		scrollToElement(addToCartBtn);
		wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
		js.executeScript("arguments[0].click();", addToCartBtn);

		System.out.println("✔ Product added to cart");
	}
}
