package Form_testing;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Base.BaseClass;

public class PDP extends BaseClass {

	 // Helper method to click product in new tab safely
    private void clickProductInNewTab(WebElement product) {
        String parent = driver.getWindowHandle();
        act.keyDown(Keys.CONTROL).click(product).keyUp(Keys.CONTROL).perform();

        // Switch to new tab
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(parent)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        // Optional: print product info
        System.out.println("Opened tab: " + driver.getTitle());

        // Close new tab and switch back
        driver.close();
        driver.switchTo().window(parent);
    }

    @Test(priority = 1)
    public void TC_01_ClickOnProductRecommendations() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        js.executeScript("window.scrollBy(0, 5800)");

        List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//form[@class='home-cat-iteam']")));

        for (WebElement product : products) {
            System.out.println(product.getText());
            clickProductInNewTab(product);

            // Refresh elements after switching back
            products = driver.findElements(By.xpath("//form[@class='home-cat-iteam']"));
        }

        js.executeScript("window.scrollTo(0, 0)");
    }

    @Test(priority = 2)
    public void TC_02_ClickOnAddToCartButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='add-tocart-btn']")));
        addToCartBtn.click();
    }
}