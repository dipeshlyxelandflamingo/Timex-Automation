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
	    JavascriptExecutor js;

	    public MiniCartPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        this.js = (JavascriptExecutor) driver;
	    }

	    // ---------- Quantity Increase ----------
	    public void increaseQuantity(int times) {

	        WebElement qtyInput = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='qty']")));

	        js.executeScript("arguments[0].scrollIntoView({block:'center'});", qtyInput);

	        int currentQty = Integer.parseInt(qtyInput.getAttribute("value"));
	        int targetQty = currentQty + times;

	        int attempts = 0;

	        while (currentQty < targetQty && attempts < times * 3) {

	            WebElement plusBtn = wait.until(
	                    ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='qty-plus']")));

	            js.executeScript("arguments[0].scrollIntoView({block:'center'});", plusBtn);
	            js.executeScript("arguments[0].click();", plusBtn);

	            final int expectedQty = currentQty + 1;

	            wait.until(new ExpectedCondition<Boolean>() {
	                @Override
	                public Boolean apply(WebDriver driver) {
	                    int val = Integer.parseInt(
	                            driver.findElement(By.xpath("//input[@class='qty']")).getAttribute("value"));
	                    return val == expectedQty;
	                }
	            });

	            qtyInput = driver.findElement(By.xpath("//input[@class='qty']"));
	            currentQty = Integer.parseInt(qtyInput.getAttribute("value"));
	            attempts++;
	        }

	        Assert.assertEquals(currentQty, targetQty, "❌ Quantity increase failed");
	        System.out.println("✔ Quantity increased successfully: " + currentQty);
	    }

	    // ---------- Enter Pincode ----------
	    public void enterPincode(String pincode) {

	        WebElement pincodeInput = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.id("userPincode")));

	        js.executeScript("arguments[0].scrollIntoView({block:'center'});", pincodeInput);
	        pincodeInput.clear();
	        pincodeInput.sendKeys(pincode);

	        System.out.println("✔ Pincode entered successfully");
	    }

	    // ---------- Quick Checkout ----------
	    public void goToQuickCheckout() {

	        WebElement checkoutBtn = wait.until(
	                ExpectedConditions.elementToBeClickable(By.id("gokwik-checkout-btn")));

	        js.executeScript("arguments[0].scrollIntoView({block:'center'});", checkoutBtn);
	        js.executeScript("arguments[0].click();", checkoutBtn);

	        System.out.println("✔ Quick checkout initiated");
	    }
	}