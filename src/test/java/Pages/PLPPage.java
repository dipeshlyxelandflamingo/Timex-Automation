package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PLPPage {

	WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public PLPPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // ---------------- Show Filter ----------------

    public void ClickShowFilter() throws InterruptedException {
        WebElement filterBtn =
                driver.findElement(By.xpath("//button[@aria-label='Show Filter Hide Filter']"));

        js.executeScript("arguments[0].click();", filterBtn);
        Thread.sleep(2000);
    }

    // ---------------- Band Color ----------------

    public void selectBandColorFilter(String color, int expectedCount) throws InterruptedException {

        js.executeScript("window.scrollTo(0,0)");
        Thread.sleep(1000);

        js.executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//button[text()='Gender']")));
        js.executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//button[text()='Price']")));

        Thread.sleep(1000);

        WebElement filter =
                driver.findElement(By.xpath("//button[text()='Band Color']"));

        js.executeScript("arguments[0].scrollIntoView(true);", filter);
        js.executeScript("arguments[0].click();", filter);

        WebElement option =
                driver.findElement(By.xpath("//button[@title='" + color + "']"));
        js.executeScript("arguments[0].click();", option);

        Thread.sleep(1500);
        js.executeScript("arguments[0].click();", filter);

        validateProductCount(expectedCount);
        clearAllFilters();
    }

    // ---------------- Dial Color ----------------

    public void selectDialColorFilter(String color, int expectedCount) throws InterruptedException {

        WebElement filter =
                driver.findElement(By.xpath("//button[text()='Dial Color']"));

        js.executeScript("arguments[0].scrollIntoView(true);", filter);
        js.executeScript("arguments[0].click();", filter);

        WebElement option =
                driver.findElement(By.xpath("//button[@title='" + color + "']"));
        js.executeScript("arguments[0].click();", option);

        Thread.sleep(1500);
        js.executeScript("arguments[0].click();", filter);

        validateProductCount(expectedCount);
        clearAllFilters();
    }

    // ---------------- Case Diameter ----------------

    public void selectCaseDiameterFilter(String diameter, int expectedCount) throws InterruptedException {

        WebElement filter =
                driver.findElement(By.xpath("//button[text()='Case Diameter']"));

        js.executeScript("arguments[0].scrollIntoView(true);", filter);
        js.executeScript("arguments[0].click();", filter);

        WebElement option =
                driver.findElement(By.xpath("//button[@title='" + diameter + "']"));
        js.executeScript("arguments[0].click();", option);

        Thread.sleep(1500);
        js.executeScript("arguments[0].click();", filter);

        validateProductCount(expectedCount);
        clearAllFilters();
    }

    // ---------------- Band Material ----------------

    public void selectBandMaterialFilter(String material, int expectedCount) throws InterruptedException {

        WebElement filter =
                driver.findElement(By.xpath("(//div[@class='usf-title usf-no-select'])[6]"));

        js.executeScript("arguments[0].scrollIntoView(true);", filter);
        js.executeScript("arguments[0].click();", filter);

        WebElement option =
                driver.findElement(By.xpath("//button[@title='" + material + "']"));
        js.executeScript("arguments[0].click();", option);

        Thread.sleep(1500);
        js.executeScript("arguments[0].click();", filter);

        validateProductCount(expectedCount);
        clearAllFilters();
    }

    // ---------------- Product ----------------

    public void clickOnProduct() throws InterruptedException {

        js.executeScript("window.scrollTo(0,0)");
        Thread.sleep(1500);

        WebElement product =
                driver.findElement(By.xpath("//a[@href='/collections/mens/products/tw2t80700zv']"));

        js.executeScript("arguments[0].click();", product);
        Thread.sleep(3000);
    }

    // ---------------- Helpers ----------------

    private void validateProductCount(int expectedCount) {

        List<WebElement> products =
                driver.findElements(By.xpath("//form[@class='home-cat-iteam hs-event-static']"));

        int actualCount = products.size();
        Assert.assertEquals(actualCount, expectedCount,
                "❌ Product count is NOT correct after applying filter");

        System.out.println("✔ Product count is correct: " + actualCount);
    }

    private void clearAllFilters() throws InterruptedException {

        WebElement clearBtn =
                driver.findElement(By.xpath("(//button[@class='usf-clear-all usf-btn'])[1]"));

        js.executeScript("arguments[0].click();", clearBtn);
        Thread.sleep(2000);
    }
}