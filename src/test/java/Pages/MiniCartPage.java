package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MiniCartPage {
	
	WebDriver driver;
    WebDriverWait wait;

    public MiniCartPage(WebDriver driver) {
        this.driver = driver;
       
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		
        
    }

    // ---------- Quantity Increase ----------
    public void increaseQuantity(int times) {
        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='qty']")));
        int currentQty = Integer.parseInt(qtyInput.getAttribute("value"));
        int targetQty = currentQty + times;

        int attempts = 0;
        while (currentQty < targetQty && attempts < times * 3) {

            // Re-fetch plus button every iteration to avoid stale element
            WebElement plusBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='qty-plus']")));
            plusBtn.click();

            // Create final variable for anonymous class
            final int expectedQty = currentQty + 1;

            // Custom wait for quantity to update
            wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    WebElement input = driver.findElement(By.xpath("//input[@class='qty']"));
                    int val = Integer.parseInt(input.getAttribute("value"));
                    return val == expectedQty;
                }
            });

            // Re-fetch input and update current quantity
            qtyInput = driver.findElement(By.xpath("//input[@class='qty']"));
            currentQty = Integer.parseInt(qtyInput.getAttribute("value"));
            attempts++;
        }

        Assert.assertEquals(currentQty, targetQty, "❌ Quantity increase failed");
        System.out.println("✔ Quantity increased successfully: " + currentQty);
    }

 

    // ---------- Enter Pincode ----------
    public void enterPincode(String pincode) {
        WebElement pincodeInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("userPincode")));
        pincodeInput.clear();
        pincodeInput.sendKeys(pincode);
        System.out.println("✔ Pincode entered successfully");
    }

    // ---------- Quick Checkout ----------
    public void goToQuickCheckout() {
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("gokwik-checkout-btn")));
        checkoutBtn.click();
        System.out.println("✔ Quick checkout initiated");
    }
}