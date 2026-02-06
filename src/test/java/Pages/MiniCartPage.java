package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
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

    // ---------- Quantity Increase ----------
    public void increaseQuantity(int times) {

        // SAME locators (no change)
        By qtyInputBy = By.xpath("//input[@class='qty']");
        By plusBtnBy = By.xpath("//button[@class='qty-plus']");

        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(qtyInputBy));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", qtyInput);

        int currentQty = Integer.parseInt(qtyInput.getAttribute("value"));
        int targetQty = currentQty + times;

        int attempts = 0;

        while (currentQty < targetQty && attempts < times * 3) {

            WebElement plusBtn = wait.until(ExpectedConditions.elementToBeClickable(plusBtnBy));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", plusBtn);

            // click (try normal then JS)
            try {
                plusBtn.click();
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
                    } catch (Exception e) {
                        return false;
                    }
                }
            });

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
        By pincodeBy = By.id("userPincode");

        WebElement pincodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(pincodeBy));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", pincodeInput);

        try {
            pincodeInput.clear();
        } catch (Exception ignored) {}

        pincodeInput.sendKeys(pincode);

        System.out.println("✔ Pincode entered successfully");
    }

    // ---------- Quick Checkout ----------
    public void goToQuickCheckout() {

        // SAME locator (no change)
        By checkoutBtnBy = By.id("gokwik-checkout-btn");

        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtnBy));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", checkoutBtn);

        try {
            checkoutBtn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", checkoutBtn);
        }

        System.out.println("✔ Quick checkout initiated");
    }
}