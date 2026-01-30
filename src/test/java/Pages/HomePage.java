package Pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    /* ===================== Helper ===================== */

    private void openInNewTabAndClose(WebElement element) {
        String parent = driver.getWindowHandle();

        String url = element.getAttribute("href");
        js.executeScript("window.open(arguments[0], '_blank');", url);

        // switch to new tab
        Set<String> windows = driver.getWindowHandles();
        for (String win : windows) {
            if (!win.equals(parent)) {
                driver.switchTo().window(win);
                break;
            }
        }

        // close child tab & switch back
        driver.close();
        driver.switchTo().window(parent);
    }

    /* ---------------- TC_02 ---------------- */

    public void clickMarlinShopNowAndCloseTab() {

        WebElement shopNowBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[@href='/products/timex-men-green-analogue-round-stainless-steel-dial-watch-tw2v44600u9'])[2]")));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", shopNowBtn);
        wait.until(ExpectedConditions.elementToBeClickable(shopNowBtn));

        openInNewTabAndClose(shopNowBtn);
    }

    /* ---------------- TC_03 ---------------- */

    public void clickLatestReleaseProducts() {

        List<WebElement> products = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='product-bx']")));

        for (WebElement product : products) {
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", product);
            wait.until(ExpectedConditions.elementToBeClickable(product));

            openInNewTabAndClose(product);
        }
    }

    /* ---------------- TC_04 ---------------- */

    public void clickPopularCategory() {

        WebElement category = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[@href='/collections/offers'])[3]")));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", category);
        wait.until(ExpectedConditions.elementToBeClickable(category));

        openInNewTabAndClose(category);
    }

    /* ---------------- TC_05 ---------------- */

    public void clickLeatherStrapWatch() {

        WebElement product = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[@href='/products/timex-continental-black-round-dial-analog-mens-watch-tw2y25000uj'])[2]")));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", product);
        wait.until(ExpectedConditions.elementToBeClickable(product));

        openInNewTabAndClose(product);
    }

    /* ---------------- TC_06 ---------------- */

    public void clickMoreToLoveSection() {

        WebElement moreSection = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[@href='/collections/timex-x-jacquie-aiche'])[4]")));

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", moreSection);
        wait.until(ExpectedConditions.elementToBeClickable(moreSection));

        openInNewTabAndClose(moreSection);
    }

    /* ---------------- TC_07 ---------------- */

    public void goToWatchesCategory() {

        By watchesLocator = By.xpath("//a[contains(@class,'nav_link_timex')]");
        By shopAllLocator = By.xpath("//a[contains(@href,'/collections/mens')]");

        for (int i = 0; i < 2; i++) {
            try {

                WebElement watchesTab = wait.until(
                        ExpectedConditions.elementToBeClickable(watchesLocator)
                );
                watchesTab.click();

                WebElement shopAll = wait.until(
                        ExpectedConditions.elementToBeClickable(shopAllLocator)
                );
                shopAll.click();

                break;

            } catch (Exception e) {
                System.out.println("Retrying Test 7 due to: " + e);
            }
        }
    }

}