package Base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
	        driver = new ChromeDriver();

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