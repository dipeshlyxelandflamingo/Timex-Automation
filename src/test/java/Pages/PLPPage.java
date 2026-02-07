package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PLPPage {

	WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    Actions actions;

    public PLPPage(WebDriver driver) {
        this.driver = driver;

        // Linux headless me thoda higher wait (no xpath/logic change)
        boolean isLinux = System.getProperty("os.name", "").toLowerCase().contains("linux");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(isLinux ? 35 : 25));

        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    public void clickshowfilter() {

        By filterBtn = By.xpath("//span[@class='collection-filters__button-spacing']");
        By filterPanel = By.xpath("//div[contains(@class,'usf-facets') and contains(@class,'usf-sr-filters')]");

        // 1) Wait till button exists + visible
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(filterBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);

        // 2) Click + wait panel (retry 2 times)
        for (int i = 0; i < 3; i++) {
            try {
                // IMPORTANT: clickable(By) to avoid stale WebElement in headless
                wait.until(ExpectedConditions.elementToBeClickable(filterBtn));
                js.executeScript("arguments[0].click();", driver.findElement(filterBtn));

                // 3) Wait till side filter panel opens
                wait.until(ExpectedConditions.visibilityOfElementLocated(filterPanel));
                return;

            } catch (Exception e) {
                btn = wait.until(ExpectedConditions.visibilityOfElementLocated(filterBtn));
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
            }
        }

        Assert.fail("Filter panel did not open after clicking Show Filter button");
    }

    public void scrollAndClick(By locator) {
        // IMPORTANT: use By-based wait to reduce stale issues
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", driver.findElement(locator));
        }
    }

    public void closeGenderandpricefilter() throws Exception {

        By closegenderfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[1]");
        scrollAndClick(closegenderfilter);

        Thread.sleep(800);
        By closepricefilter = By.xpath("(//div[@class='usf-title usf-no-select'])[2]");
        scrollAndClick(closepricefilter);
    }

    public void openandselectbandcolorfilter(String colorName, int expectedCount) throws Exception {

        By openandclosebandfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[3]");
        scrollAndClick(openandclosebandfilter);

        By bandColorBtn = By.xpath("//div[contains(@class,'usf-facet-values--Swatch')]//button[.//span[normalize-space()='"
                + colorName + "']]");

        WebElement selectbandfilter = wait.until(ExpectedConditions.elementToBeClickable(bandColorBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectbandfilter);
        js.executeScript("arguments[0].click();", selectbandfilter);

        waitForProductsToLoad();

        // ✅ Cleanup ALWAYS runs, even if validate fails
        try {
            scrollAndClick(openandclosebandfilter);
            validateProductCount(expectedCount);
        } finally {
            try { ClickOnClearbutton(); } catch (Exception ignored) {}
        }

        Thread.sleep(800);
    }

    public void openandselectDialcolorfilter(String colorName, int expectedCount) throws Exception {

        By openandclosedialcolorfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[4]");
        scrollAndClick(openandclosedialcolorfilter);

        By dialColorBtn = By.xpath("//div[contains(@class,'usf-facet-values--Swatch')]//button[.//span[normalize-space()='"
                + colorName + "']]");

        WebElement selectbandfilter = wait.until(ExpectedConditions.elementToBeClickable(dialColorBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectbandfilter);
        js.executeScript("arguments[0].click();", selectbandfilter);

        waitForProductsToLoad();

        // ✅ Cleanup ALWAYS runs
        try {
            scrollAndClick(openandclosedialcolorfilter);
            validateProductCount(expectedCount);
        } finally {
            try { ClickOnClearbutton(); } catch (Exception ignored) {}
        }

        Thread.sleep(800);
    }

    public void openandselectCaseDiameterfilter(String casediameterName, int expectedCount) throws Exception {

        By openCaseDiameterfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[5]");
        scrollAndClick(openCaseDiameterfilter);

        By caseDiameterBtn = By.xpath("//button[.//span[contains(@class,'usf-label') and normalize-space()='"
                + casediameterName + "']]");

        WebElement selectbandfilter = wait.until(ExpectedConditions.elementToBeClickable(caseDiameterBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectbandfilter);
        js.executeScript("arguments[0].click();", selectbandfilter);

        waitForProductsToLoad();

        By closeCaseDiameterfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[3]");

        // ✅ Cleanup ALWAYS runs
        try {
            scrollAndClick(closeCaseDiameterfilter);
            validateProductCount(expectedCount);
        } finally {
            try { ClickOnClearbutton(); } catch (Exception ignored) {}
        }

        Thread.sleep(800);
    }

    public void openandselectbandmaterialfilter(String bandMaterial, int expectedCount) throws Exception {

        By openbandmaterialfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[6]");
        scrollAndClick(openbandmaterialfilter);

        By bandMaterialBtn = By.xpath("//div[contains(@class,'usf-facet-values--Box')]//button[.//span[normalize-space()='"
                + bandMaterial + "']]");

        WebElement selectbandfilter = wait.until(ExpectedConditions.elementToBeClickable(bandMaterialBtn));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", selectbandfilter);
        js.executeScript("arguments[0].click();", selectbandfilter);

        waitForProductsToLoad();

        By closebandmaterialfilter = By.xpath("(//div[@class='usf-title usf-no-select'])[5]");

        // ✅ Cleanup ALWAYS runs
        try {
            scrollAndClick(closebandmaterialfilter);
            validateProductCount(expectedCount);
        } finally {
            try { ClickOnClearbutton(); } catch (Exception ignored) {}
        }

        Thread.sleep(800);
    }

    public void clickProducts(int index) throws Exception {

        Thread.sleep(800);
        By productLinks = By.xpath("//a[contains(@href,'/collections/mens/products/') and contains(@class,'prd-h-img')]");
        // DOM me yahi class dikh rahi hai

        List<WebElement> links = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(productLinks)
        );

        if (links.size() == 0) {
            Assert.fail("No product links found on PLP");
        }

        if (index >= links.size()) {
            Assert.fail("Index " + index + " out of range. Total products: " + links.size());
        }

        WebElement product = links.get(index);

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", product);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(product)).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", product);
        }
    }
    

    private void waitForProductsToLoad() {

        By summaryB = By.xpath("//span[contains(@class,'usf-sr-summary')]//b");
        WebElement summary = wait.until(ExpectedConditions.visibilityOfElementLocated(summaryB));

        // oldText final for lambda safety (no logic change)
        final String oldText = summary.getText().trim();

        By anySpinner = By.cssSelector(".usf-loader, .usf-loading, .loading, .spinner");

        try {
            wait.until(d -> {
                try {
                    if (!d.findElements(anySpinner).isEmpty()) {
                        for (WebElement sp : d.findElements(anySpinner)) {
                            if (sp.isDisplayed()) return false;
                        }
                    }

                    String newText = d.findElement(summaryB).getText().trim();
                    return !newText.equals(oldText);

                } catch (StaleElementReferenceException e) {
                    return true;
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (Exception e) {
        }
    }

    private void validateProductCount(int expectedCount) {

        By summaryB = By.xpath("//span[contains(@class,'usf-sr-summary')]//b");
        WebElement summary = wait.until(ExpectedConditions.visibilityOfElementLocated(summaryB));

        String summaryText = summary.getText().trim();
        int actualCount = Integer.parseInt(summaryText.replaceAll("[^0-9]", ""));

        Assert.assertEquals(actualCount, expectedCount, "Expected product count does not match filtered result");
    }

    private void ClickOnClearbutton() {

        By clearBtn = By.xpath("(//button[contains(@class,'usf-clear-all')])[1]");
        WebElement clear = wait.until(ExpectedConditions.elementToBeClickable(clearBtn));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", clear);

        try {
            clear.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", clear);
        }
    }
}