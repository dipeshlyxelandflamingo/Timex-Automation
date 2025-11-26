package Form_testing;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseClass;

public class PLP_Page extends BaseClass {

	// Helper method to select filter, verify selection, and check product count
    private void applyFilterAndVerify(String filterXPath, String optionXPath, int expectedCount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(filterXPath)));
        filter.click();

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXPath)));
        option.click();

        String selectedClass = option.getAttribute("class");
        Assert.assertTrue(selectedClass.contains("active") || selectedClass.contains("selected"),
                "❌ Filter option is NOT selected: " + optionXPath);

        System.out.println("✔ Filter option selected: " + optionXPath);

        // Wait for products to load
        List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//form[@class='home-cat-iteam hs-event-static']")));

        int actualCount = products.size();
        Assert.assertEquals(actualCount, expectedCount,
                "❌ Product count is NOT correct after applying filter");
        System.out.println("✔ Product count is correct: " + actualCount);

        // Clear filter
        WebElement clearBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='usf-clear-all usf-btn']")));
        clearBtn.click();
    }

    @Test(priority = 1)
    public void TC_01_SelectBandColorFilter() {
        applyFilterAndVerify("//div[@class ='usf-facet usf-collapsed']",
                "//button[@title='Blue (15)']", 15);
    }

    @Test(priority = 2)
    public void TC_02_SelectDialColorFilter() {
        applyFilterAndVerify("(//div[@class='usf-title usf-no-select'])[4]",
                "//button[@title='Brown (11)']", 11);
    }

    @Test(priority = 3)
    public void TC_03_SelectCaseDiameterFilter() {
        applyFilterAndVerify("(//div[@class='usf-title usf-no-select'])[5]",
                "//button[@title='32.5 MM (3)']", 3);
    }

    @Test(priority = 4)
    public void TC_04_SelectBandMaterialFilter() {
        applyFilterAndVerify("(//div[@class='usf-title usf-no-select'])[6]",
                "//button[@title='Black (21)']", 21);
    }

    @Test(priority = 5)
    public void TC_05_ClickOnProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js.executeScript("window.scrollTo(0, 0)");

        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/collections/mens/products/tw2t80700zv']")));
        productLink.click();
    }
}