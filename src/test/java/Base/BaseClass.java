package Base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	 protected WebDriver driver;
	    protected JavascriptExecutor js;
	    protected Actions act;
	    protected WebDriverWait wait;

	    @BeforeClass
	    public void setUpClass() {
	    	
	    	  WebDriverManager.chromedriver().setup();

	          ChromeOptions options = new ChromeOptions();
	          options.addArguments("--disable-blink-features=AutomationControlled");
	          options.addArguments("start-maximized");
	          options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.7444.134 Safari/537.36");

	          if (System.getProperty("os.name").toLowerCase().contains("linux")) {
	              options.addArguments("--headless=new");
	              options.addArguments("--no-sandbox");
	              options.addArguments("--disable-dev-shm-usage");
	              options.addArguments("--disable-gpu");
	              options.addArguments("--window-size=1920,1080");
	          }

	          options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
	          options.setExperimentalOption("useAutomationExtension", false);
	          options.addArguments("--remote-allow-origins=*");

	          driver = new ChromeDriver(options);

	       

	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

	        driver.get("https://shop.timexindia.com");

	        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        js = (JavascriptExecutor) driver;
	        act = new Actions(driver);

	        handlePopupById();
	    }

	    private void handlePopupById() {
	        try {
	            By popupBtn = By.id("md-btn__form__onSubmit");
	            wait.until(ExpectedConditions.elementToBeClickable(popupBtn)).click();
	            System.out.println("Popup closed successfully");
	        } catch (Exception e) {
	            System.out.println("Popup not present");
	        }
	    }

	   @AfterClass
	    public void tearDownClass() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}