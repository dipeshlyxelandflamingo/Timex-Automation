package Pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class MiniCartPage {
	
	WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public MiniCartPage(WebDriver driver) {
        this.driver = driver;

        boolean isLinux = System.getProperty("os.name", "").toLowerCase().contains("linux");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(isLinux ? 35 : 20));

        this.js = (JavascriptExecutor) driver;
    }

    // ✅ Alert handler (CI/headless safe)
    private void handleAlertIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            shortWait.until(ExpectedConditions.alertIsPresent());

            Alert alert = driver.switchTo().alert();
            String txt = "";
            try { txt = alert.getText(); } catch (Exception ignored) {}

            System.out.println("⚠ Alert found: " + txt);

            try { alert.accept(); } catch (Exception ignored) {}
            driver.switchTo().defaultContent();

        } catch (TimeoutException | NoAlertPresentException e) {
            // no alert
        } catch (Exception e) {
            // ignore
        }
    }

    // ✅ Wait until mini cart is visible (auto-open after add to cart)
    private void waitForMiniCartVisible() {

        // Either cart empty msg OR qty input should appear
        By emptyText = By.xpath("//*[contains(text(),'Your cart is currently empty')]");
        By qtyInput  = By.xpath("//input[@class='qty']");

        try {
            wait.until(d -> !d.findElements(emptyText).isEmpty() || !d.findElements(qtyInput).isEmpty());
        } catch (Exception e) {
            // continue, failIfCartEmpty will handle properly
        }
    }

    // ✅ Fail early if cart is empty
    public void failIfCartEmpty() {

        waitForMiniCartVisible();

        By emptyText = By.xpath("//*[contains(text(),'Your cart is currently empty')]");
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement el = shortWait.until(ExpectedConditions.visibilityOfElementLocated(emptyText));
            if (el != null) {
                Assert.fail("❌ Mini cart empty: product add to cart failed (PDP/Backend).");
            }
        } catch (TimeoutException e) {
            System.out.println("✔ Mini cart has product (not empty).");
        }
    }

    // ---------- Quantity Increase (keep it, you won't run it now) ----------
    public void increaseQuantity(int times) {
        // left as-is (optional). Call failIfCartEmpty() at start if you use it later.
        failIfCartEmpty();
        // your existing increaseQuantity code can remain here
    }

    // ---------- Enter Pincode ----------
    public void enterPincode(String pincode) {

        handleAlertIfPresent();
        failIfCartEmpty();

        By pincodeBy = By.xpath("//input[@id='userPincode']");

        WebElement pincodeInput;
        try {
            pincodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(pincodeBy));
        } catch (UnhandledAlertException ua) {
            handleAlertIfPresent();
            pincodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(pincodeBy));
        }

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", pincodeInput);

        try { pincodeInput.clear(); } catch (Exception ignored) {}

        try {
            pincodeInput.sendKeys(pincode);
        } catch (UnhandledAlertException ua) {
            handleAlertIfPresent();
            pincodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(pincodeBy));
            try { pincodeInput.clear(); } catch (Exception ignored) {}
            pincodeInput.sendKeys(pincode);
        }

        System.out.println("✔ Pincode entered successfully");
    }

    // ---------- Quick Checkout ----------
    public void goToQuickCheckout() {

        handleAlertIfPresent();
        failIfCartEmpty();

        By checkoutBtnBy = By.id("gokwik-checkout-btn");

        WebElement checkoutBtn;
        try {
            checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtnBy));
        } catch (UnhandledAlertException ua) {
            handleAlertIfPresent();
            checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtnBy));
        }

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", checkoutBtn);

        try {
            checkoutBtn.click();
        } catch (UnhandledAlertException ua) {
            handleAlertIfPresent();
            checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtnBy));
            js.executeScript("arguments[0].click();", checkoutBtn);
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", checkoutBtn);
        }

        System.out.println("✔ Quick checkout initiated");
    }
}