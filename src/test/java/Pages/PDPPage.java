package Pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.TimeoutException;
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

        // ✅ wait for new tab handle to appear (headless needs this)
        try {
            wait.until(d -> d.getWindowHandles().size() > beforeCount);
        } catch (TimeoutException te) {
            // one retry (sometimes tab handle registers late in CI)
            js.executeScript("window.open(arguments[0], '_blank');", url);
            wait.until(d -> d.getWindowHandles().size() > beforeCount);
        }

        // switch to new tab
        Set<String> windows = driver.getWindowHandles();
        String child = null;
        for (String win : windows) {
            if (!win.equals(parent)) {
                child = win;
            }
        }

        if (child != null) {
            driver.switchTo().window(child);
            driver.close();
        }

        driver.switchTo().window(parent);
    }

    private void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    /* ---------------- Product Recommendation ---------------- */

    public void clickOnProductRecommendationsAndCloseTab() {

        By productsBy = By.xpath("//form[@class='home-cat-iteam ']");

        // same logic: get list and iterate, but stable: re-fetch by index
        List<WebElement> products = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(productsBy));

        int count = products.size();

        for (int i = 0; i < count; i++) {

            // ✅ re-fetch fresh list each iteration (prevents stale in headless)
            products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productsBy));
            WebElement product = products.get(i);

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
                    product
            );

            // ✅ clickable via JS click fallback not needed here; we open via href
            // but wait until element is visible/stable
            wait.until(ExpectedConditions.visibilityOf(product));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            openInNewTabAndClose(product);
        }
    }

    /* ---------------- Add To Cart ---------------- */

    public void addProductToCart() {

        By addToCartBtnBy = By.xpath("//button[@class='add-tocart-btn']");

        // 1️⃣ Button visible hone do
        WebElement addToCartBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(addToCartBtnBy));

        // 2️⃣ Button ko top ke paas lao
        js.executeScript(
                "arguments[0].scrollIntoView({block:'start', inline:'nearest'});",
                addToCartBtn
        );

        // 3️⃣ Thoda sa upar scroll
        js.executeScript("window.scrollBy(0, -80);");

        // 4️⃣ Clickable wait (By based - stable)
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtnBy));

        // 5️⃣ Click (safe + fallback)
        try {
            driver.findElement(addToCartBtnBy).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", driver.findElement(addToCartBtnBy));
        }

        // 6️⃣ If alert comes (CI/headless), accept it and FAIL with clear message
        //    (alert text: "Unable to add Product, Please try again Later.")
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            org.openqa.selenium.Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            String text = "";
            try { text = alert.getText(); } catch (Exception ignored) {}
            alert.accept();
            throw new AssertionError("Add to Cart failed due to alert: " + text);
        } catch (org.openqa.selenium.TimeoutException ignored) {
            // no alert, continue
        }

        // 7️⃣ VERIFY product really added (very important for Jenkins)
        //    We check if qty input is visible (your MiniCart uses this same xpath)
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='qty']")));
            System.out.println("✔ Product added to cart (verified)");
        } catch (Exception e) {
            throw new AssertionError("Add to Cart clicked but product NOT added (qty not visible). CI/backend issue.");
        }
    }
    
}