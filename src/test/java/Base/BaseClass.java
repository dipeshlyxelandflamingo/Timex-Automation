package Base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseClass {

	 public WebDriver driver;
	    public JavascriptExecutor js;
	    public Actions act;
	    public WebDriverWait wait;

	    @BeforeClass
	    public void setUpClass() {

	        boolean isLinux = System.getProperty("os.name", "").toLowerCase().contains("linux");

	        WebDriverManager.chromedriver().setup();

	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--disable-blink-features=AutomationControlled");
	        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

	        // Good stability flags for CI/headless too
	        options.addArguments("--disable-notifications");
	        options.addArguments("--disable-popup-blocking");
	        options.addArguments("--lang=en-US");
	        options.addArguments("--remote-allow-origins=*");

	        // ✅ IMPORTANT: Auto-accept JS alerts (fix UnhandledAlertException in CI)
	        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

	        if (isLinux) {
	            options.addArguments("--headless=new");
	            options.addArguments("--no-sandbox");
	            options.addArguments("--disable-dev-shm-usage");
	            options.addArguments("--disable-gpu");
	            options.addArguments("--window-size=1920,1080");
	        }

	        options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
	        options.setExperimentalOption("useAutomationExtension", false);

	        driver = new ChromeDriver(options);

	        // ✅ Do NOT mix implicit + explicit waits (CI flakiness)
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
	        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(60));

	        // Maximize only on non-headless runs
	        if (!isLinux) {
	            driver.manage().window().maximize();
	        }

	        // CI needs bigger waits (Linux server slower)
	        wait = new WebDriverWait(driver, Duration.ofSeconds(isLinux ? 35 : 20));

	        js = (JavascriptExecutor) driver;
	        act = new Actions(driver);

	        driver.get("https://shop.timexindia.com");

	        waitForPageReady();
	        handlePopupById();
	    }

	    private void waitForPageReady() {
	        try {
	            wait.until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
	        } catch (Exception ignored) {
	        }
	    }

	    private void handlePopupById() {
	        // same logic: click popup button if present
	        try {
	            By popupBtn = By.id("md-btn__form__onSubmit");
	            // short dedicated wait so we don't waste 35s if popup isn't there
	            WebDriverWait smallWait = new WebDriverWait(driver, Duration.ofSeconds(3));
	            smallWait.until(ExpectedConditions.elementToBeClickable(popupBtn)).click();
	        } catch (Exception e) {
	            System.out.println("Popup not present");
	        }
	    }

	    @AfterClass(alwaysRun = true)
	    public void tearDownClass() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}