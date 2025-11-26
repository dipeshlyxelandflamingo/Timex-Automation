package Form_testing;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Base.BaseClass;

public class Homepage extends BaseClass {

	 // Helper method: open new tab, do actions, close tab safely
    private void clickElementInNewTab(WebElement element) {
        String parent = driver.getWindowHandle();
        act.keyDown(Keys.CONTROL).click(element).keyUp(Keys.CONTROL).perform();

        // Switch to new tab
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(parent)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        // Optional: do something in new tab (like print title)
        System.out.println("Opened tab: " + driver.getTitle());

        // Close new tab
        driver.close();

        // Switch back to parent
        driver.switchTo().window(parent);
    }

    @Test(priority = 1)
    void TC_01_ClosePopUp() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement popupCloseButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("md-btn__form__onSubmit")));
        popupCloseButton.click();
    }

    @Test(priority = 2)
    void TC_02_MarlinAutomatic_ShopNowButton() {
        js.executeScript("window.scrollBy(0, 1400)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement shopNowButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "(//a[@href='/products/timex-men-green-analogue-round-stainless-steel-dial-watch-tw2v44600u9'])[2]")));

        clickElementInNewTab(shopNowButton);
    }

    @Test(priority = 3)
    void TC_03_ClickOnLatestReleaseAndBestSellerProducts() {
        js.executeScript("window.scrollBy(0, 500)");

        List<WebElement> products = driver.findElements(By.xpath("//div[@class='product-bx']"));

        for (WebElement product : products) {
            System.out.println(product.getText());
            clickElementInNewTab(product);

            // Refresh elements after switching back
            products = driver.findElements(By.xpath("//div[@class='product-bx']"));
        }
    }

    @Test(priority = 4)
    void TC_04_ClickOnPopularCategory() {
        js.executeScript("window.scrollBy(0, 600)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bestSeller = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[@href='/collections/offers'])[9]")));

        clickElementInNewTab(bestSeller);
    }

    @Test(priority = 5)
    void TC_05_ClickOnLeatherStrapWatch() {
        js.executeScript("window.scrollBy(0, 600)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement watch = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "(//a[@href='/products/timex-men-burgundy-stainless-steel-tonneau-dial-analog-watch-tw2v25400u9'])[6]")));

        clickElementInNewTab(watch);
    }

    @Test(priority = 6)
    void TC_06_ClickOnMoretoLoveSection() {
        js.executeScript("window.scrollBy(0, 3500)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement collection = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[@href='/collections/timex-x-jacquie-aiche'])[4]")));

        clickElementInNewTab(collection);
    }

    @Test(priority = 7)
    void TC_07_ClickOnWatchesCategoryPage() {
        js.executeScript("window.scrollTo(0, 0)");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement watchesTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='nav_link_timex']")));
        watchesTab.click();

        WebElement shopAll = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/collections/mens']")));
        shopAll.click();
    }
}