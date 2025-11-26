package Form_testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseClass;

public class MiniCart extends BaseClass{
	
	
	WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

    @Test(priority = 1)
    public void TC_01_QuantityIncrease() {
        int expectedQty = 5;

        WebElement plusBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='qty-plus']")));
        WebElement qtyInput = driver.findElement(By.xpath("//input[@class='qty']"));

        for (int i = 1; i <= 4; i++) {
            plusBtn.click();
            wait.until(ExpectedConditions.attributeToBe(qtyInput, "value", String.valueOf(i + 1)));
        }

        int actualQty = Integer.parseInt(qtyInput.getAttribute("value"));
        Assert.assertEquals(actualQty, expectedQty, "❌ Quantity not increased correctly");
        System.out.println("✔ Quantity Increased Successfully. Final Quantity: " + actualQty);
    }

    @Test(priority = 2)
    public void TC_02_QuantityDecrease() {
        int expectedQty = 1;

        WebElement minusBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='qty-minus']")));
        WebElement qtyInput = driver.findElement(By.xpath("//input[@class='qty']"));

        for (int i = 4; i >= 1; i--) {
            minusBtn.click();
            wait.until(ExpectedConditions.attributeToBe(qtyInput, "value", String.valueOf(i)));
        }

        int actualQty = Integer.parseInt(qtyInput.getAttribute("value"));
        Assert.assertEquals(actualQty, expectedQty, "❌ Quantity not decreased correctly");
        System.out.println("✔ Quantity Decreased Successfully. Final Quantity: " + actualQty);
    }

    @Test(priority = 3)
    public void TC_03_EnterPincode() {
        WebElement pincodeInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("userPincode")));
        pincodeInput.clear();
        pincodeInput.sendKeys("201306");
    }

    @Test(priority = 4)
    public void TC_04_GoQuickCheckout() {
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("gokwik-checkout-btn")));
        checkoutBtn.click();
    }
}