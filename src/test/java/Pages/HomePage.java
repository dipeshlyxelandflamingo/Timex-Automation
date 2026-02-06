package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;

	public HomePage(WebDriver driver) {
		this.driver = driver;

		boolean isLinux = System.getProperty("os.name", "").toLowerCase().contains("linux");
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(isLinux ? 35 : 20));

		this.js = (JavascriptExecutor) driver;
	}

	/* ===================== Helpers ===================== */

	private void scrollCenter(WebElement el) {
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
	}

	private void waitForPageReady() {
		try {
			wait.until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
		} catch (Exception ignored) {
		}
	}

	private WebElement visible(By by) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	private void safeClick(By by) {
		waitForPageReady();
		WebElement el = visible(by);
		scrollCenter(el);

		try {
			wait.until(ExpectedConditions.elementToBeClickable(by)).click();
		} catch (Exception e) {
			// headless me overlay/sticky header ke case me fallback
			js.executeScript("arguments[0].click();", driver.findElement(by));
		}
		waitForPageReady();
	}

	private void openInNewTabAndClose(By linkLocator) {

		waitForPageReady();

		// parent + handles snapshot
		String parent = driver.getWindowHandle();
		Set<String> before = driver.getWindowHandles();

		WebElement element = visible(linkLocator);
		scrollCenter(element);

		String url = element.getAttribute("href");

		// Same logic: open new tab
		js.executeScript("window.open(arguments[0], '_blank');", url);

		// Wait until a new window handle appears (headless needs this)
		try {
			wait.until(d -> d.getWindowHandles().size() > before.size());
		} catch (TimeoutException te) {
			// fallback: try again once
			js.executeScript("window.open(arguments[0], '_blank');", url);
			wait.until(d -> d.getWindowHandles().size() > before.size());
		}

		// Switch to the newest handle (not parent)
		List<String> handles = new ArrayList<>(driver.getWindowHandles());
		String child = null;
		for (String h : handles) {
			if (!h.equals(parent))
				child = h;
		}

		if (child != null) {
			driver.switchTo().window(child);
			waitForPageReady();
			driver.close();
		}

		driver.switchTo().window(parent);
		waitForPageReady();
	}

	/* ---------------- TC_02 ---------------- */

	public void clickMarlinShopNowAndCloseTab() {
		By shopNowBtn = By.xpath(
				"(//a[@href='/products/timex-men-green-analogue-round-stainless-steel-dial-watch-tw2v44600u9'])[2]");
		openInNewTabAndClose(shopNowBtn);
	}

	/* ---------------- TC_03 ---------------- */

	public void clickLatestReleaseProducts() {

		By productCards = By.xpath("//div[@class='product-bx']");

		waitForPageReady();
		List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));

		int count = products.size();

		// IMPORTANT: re-fetch list each time to avoid stale after opening/closing tab
		for (int i = 0; i < count; i++) {
			products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));
			WebElement product = products.get(i);

			scrollCenter(product);

			// product card ke andar first link
			WebElement link = product.findElement(By.cssSelector("a[href]"));
			String href = link.getAttribute("href");

			// Same logic: open that href in new tab and close
			js.executeScript("window.open(arguments[0], '_blank');", href);

			// handle tab switching via our stable method
			// (we reuse the window handle logic)
			// create a temp locator to re-use openInNewTabAndClose pattern
			// but we already opened, so switch & close:
			String parent = driver.getWindowHandle();
			Set<String> before = driver.getWindowHandles();

			// wait new tab to register (size increases)
			wait.until(d -> d.getWindowHandles().size() >= before.size());

			// find newest non-parent
			List<String> handles = new ArrayList<>(driver.getWindowHandles());
			String child = null;
			for (String h : handles) {
				if (!h.equals(parent))
					child = h;
			}

			if (child != null) {
				driver.switchTo().window(child);
				waitForPageReady();
				driver.close();
			}

			driver.switchTo().window(parent);
			waitForPageReady();
		}
	}

	/* ---------------- TC_04 ---------------- */

	public void clickPopularCategory() {
		By category = By.xpath("//a[@href='/collections/valentines-day-watch-gifts']");
		openInNewTabAndClose(category);
	}

	/* ---------------- TC_05 ---------------- */

	public void clickLeatherStrapWatch() {
		By product = By
				.xpath("(//a[@href='/products/timex-continental-black-round-dial-analog-mens-watch-tw2y25000uj'])[2]");
		openInNewTabAndClose(product);
	}

	/* ---------------- TC_06 ---------------- */

	public void clickMoreToLoveSection() {
		By moreSection = By.xpath("(//a[@href='/collections/timex-x-jacquie-aiche'])[4]");
		openInNewTabAndClose(moreSection);
	}

	/* ---------------- TC_07 ---------------- */

	public void goToWatchesCategory() {

		By watchesLocator = By.xpath("//a[contains(@class,'nav_link_timex')]");
		By shopAllLocator = By.xpath("//a[contains(@href,'/collections/mens')]");

		for (int i = 0; i < 2; i++) {
			try {
				safeClick(watchesLocator);
				safeClick(shopAllLocator);
				break;
			} catch (Exception e) {
				System.out.println("Retrying Test 7 due to: " + e);
			}
		}
	}
}