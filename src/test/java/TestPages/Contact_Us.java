package TestPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Contact_Us {

	WebDriver driver;
	

	@BeforeTest
	void setup() throws Throwable {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		
	}

	@Test(description = "TC_01_openbrowser")
	void TC_01_openbrowser() throws Throwable {
		driver.get("https://shop.timexindia.com");
		Thread.sleep(3000);
	}

	@Test(description = "TC_02_closepopup")
	void TC_02_closepopup() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement popupCloseButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("slf-close")));
		popupCloseButton.click();
		driver.navigate().to("https://shop.timexindia.com/pages/contact");		

	}

	@Test(description = "TC_03_Login")
	void TC_03_Login() throws Throwable {
				


			WebElement Firstname = driver.findElement(By.id("first_name"));			
			WebElement lastname = driver.findElement(By.id("last_name"));		
			WebElement EMail = driver.findElement(By.id("email"));			
			WebElement mobile = driver.findElement(By.id("phone"));			
			WebElement comment = driver.findElement(By.id("message"));				

			//clear all field
			Firstname.clear();
			lastname.clear();
			EMail.clear();
			mobile.clear();
			comment.clear();

			

			
			Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(0,500)");
			Thread.sleep(1000);;
			driver.findElement(By.id("contactSubmit")).click();;            
			Thread.sleep(2000);
			js.executeScript("scroll(0,-500)");
			Thread.sleep(2000);
			
			
			
	}

	@AfterTest
	public void quitBrowser() {
		driver.quit();
	}
}