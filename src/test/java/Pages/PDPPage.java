package Pages;

import java.time.Duration;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
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
        boolean isLinux = System.getProperty("os.name", "").toLowerCase().contains("linux");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(isLinux ? 35 : 15));
        this.js = (JavascriptExecutor) driver;
    }

    /* ================= Helper ================= */

    private void openInNewTabAndClose(WebElement element) {
        String parent = driver.getWindowHandle();
        int beforeCount = driver.getWindowHandles().size();

        String url = element.getAttribute("href");
        js.executeScript("window.open(arguments[0], '_blank');", url);

        try {
            wait.until(d -> d.getWindowHandles().size() > beforeCount);
        } catch (TimeoutException te) {
            js.executeScript("window.open(arguments[0], '_blank');", url);
            wait.until(d -> d.getWindowHandles().size() > beforeCount);
        }

        Set<String> windows = driver.getWindowHandles();
        String child = null;
        for (String win : windows) {
            if (!win.equals(parent)) child = win;
        }

        if (child != null) {
            driver.switchTo().window(child);
            driver.close();
        }
        driver.switchTo().window(parent);
    }

    /* ---------------- Product Recommendation ---------------- */

    public void clickOnProductRecommendationsAndCloseTab() {
        By productsBy = By.xpath("//form[@class='home-cat-iteam ']");
        List<WebElement> products = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(productsBy));

        int count = products.size();

        for (int i = 0; i < count; i++) {
            products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productsBy));
            WebElement product = products.get(i);

            js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", product);
            wait.until(ExpectedConditions.visibilityOf(product));

            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            openInNewTabAndClose(product);
        }
    }

    /* ---------------- Add To Cart ---------------- */

    // ✅ IMPORTANT: now returns true/false so Test can decide next product
    public boolean addProductToCart() {

        By addToCartBtnBy = By.xpath("//button[@class='add-tocart-btn']");

        WebElement addToCartBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(addToCartBtnBy));

        js.executeScript("arguments[0].scrollIntoView({block:'start', inline:'nearest'});", addToCartBtn);
        js.executeScript("window.scrollBy(0, -80);");

        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtnBy));

        try {
            driver.findElement(addToCartBtnBy).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", driver.findElement(addToCartBtnBy));
        }

        // ✅ If alert comes, accept and return false (do NOT stop suite)
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            String text = "";
            try { text = alert.getText(); } catch (Exception ignored) {}
            alert.accept();
            System.out.println("❌ Add to cart alert: " + text);
            return false;
        } catch (org.openqa.selenium.TimeoutException ignored) {
            // no alert, continue
        }

        // ✅ Verify cart (same xpath)
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='qty']")));
            System.out.println("✔ Product added to cart (verified)");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Add to cart clicked but qty not visible");
            return false;
        }
    }
}