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
            // short wait for alert
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            shortWait.until(ExpectedConditions.alertIsPresent());

            Alert alert = driver.switchTo().alert();
            String txt = "";
            try {
                txt = alert.getText();
            } catch (Exception ignored) {}

            System.out.println("⚠ Alert found: " + txt);

            try {
                alert.accept();
            } catch (Exception ignored) {}

            driver.switchTo().defaultContent();
        } catch (TimeoutException e) {
            // no alert
        } catch (NoAlertPresentException e) {
            // no alert
        } catch (Exception e) {
            // ignore any unexpected alert handling issue
        }
    }

    // ---------- Quantity Increase ----------
    public void increaseQuantity(int times) {

        // SAME locators (no change)
        By qtyInputBy = By.xpath("//input[@class='qty']");
        By plusBtnBy = By.xpath("//button[@class='qty-plus']");

        // just in case alert pops up before qty actions
        handleAlertIfPresent();

        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(qtyInputBy));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", qtyInput);

        int currentQty = Integer.parseInt(qtyInput.getAttribute("value"));
        int targetQty = currentQty + times;

        int attempts = 0;

        while (currentQty < targetQty && attempts < times * 3) {

            handleAlertIfPresent();

            WebElement plusBtn = wait.until(ExpectedConditions.elementToBeClickable(plusBtnBy));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", plusBtn);

            // click (try normal then JS)
            try {
                plusBtn.click();
            } catch (UnhandledAlertException ua) {
                handleAlertIfPresent();
                plusBtn = wait.until(ExpectedConditions.elementToBeClickable(plusBtnBy));
                try { plusBtn.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", plusBtn); }
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", plusBtn);
            }

            final int expectedQty = currentQty + 1;

            // wait until qty changes to expected (stale-safe)
            wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver d) {
                    try {
                        String v = d.findElement(qtyInputBy).getAttribute("value");
                        int val = Integer.parseInt(v);
                        return val == expectedQty;
                    } catch (StaleElementReferenceException e) {
                        return false;
                    } catch (UnhandledAlertException ua) {
                        return true; // will be handled outside
                    } catch (Exception e) {
                        return false;
                    }
                }
            });

            handleAlertIfPresent();

            // re-fetch fresh input (same xpath)
            qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(qtyInputBy));
            currentQty = Integer.parseInt(qtyInput.getAttribute("value"));

            attempts++;
        }

        Assert.assertEquals(currentQty, targetQty, "❌ Quantity increase failed");
        System.out.println("✔ Quantity increased successfully: " + currentQty);
    }

    // ---------- Enter Pincode ----------
    public void enterPincode(String pincode) {

        // SAME locator (no change)
        By pincodeBy = By.xpath("//input[@id='userPincode']");

        // ✅ main fix: handle alert before touching DOM
        handleAlertIfPresent();

        WebElement pincodeInput;
        try {
            pincodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(pincodeBy));
        } catch (UnhandledAlertException ua) {
            handleAlertIfPresent();
            pincodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(pincodeBy));
        }

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", pincodeInput);

        try {
            pincodeInput.clear();
        } catch (Exception ignored) {}

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

        // SAME locator (no change)
        By checkoutBtnBy = By.id("gokwik-checkout-btn");

        handleAlertIfPresent();

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
            try { checkoutBtn.click(); } catch (Exception e) { js.executeScript("arguments[0].click();", checkoutBtn); }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", checkoutBtn);
        }

        System.out.println("✔ Quick checkout initiated");
    }
}