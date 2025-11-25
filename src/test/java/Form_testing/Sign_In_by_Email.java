package Form_testing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

public class Sign_In_by_Email {

	WebDriver driver;
	FileInputStream file;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	DataFormatter formatter;
	FileOutputStream fileOut;
	XSSFCell cell;

	@BeforeTest
	void setup() throws Throwable {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		// Load Excel file
		file = new FileInputStream("C:\\Users\\LYXELANDFLAMINGO\\Desktop\\Timex\\Timex India.xlsx");
		workbook = new XSSFWorkbook(file);
		sheet = workbook.getSheet("Login via Email");
		formatter = new DataFormatter();
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
		driver.navigate().to("https://shop.timexindia.com/account/login?return_url=%2Faccount#passwordlogin");
	}

	@Test(description = "TC_03_Login")
	void TC_03_Login() throws Throwable {
		for (int i = 1; i <= 11; i++) {
			cell = sheet.getRow(i).getCell(1);
			String Email = formatter.formatCellValue(cell);

			cell = sheet.getRow(i).getCell(2);
			String Password = formatter.formatCellValue(cell);

			WebElement emailField = driver.findElement(By.id("CustomerEmail"));
			WebElement passwordField = driver.findElement(By.id("CustomerPassword"));

			System.out.println("User" + "=" + i);
			System.out.println("Email" + "=" + Email);
			System.out.println("Password" + "=" + Password);

			// Clear the input fields
			emailField.clear();
			passwordField.clear();

			// Enter the new values
			emailField.sendKeys(Email);
			Thread.sleep(1000);
			passwordField.sendKeys(Password);
			Thread.sleep(1000);

			// Click the login button	           

			// Click the login button
            WebElement loginButton = driver.findElement(By.xpath("//body/section[@id='login']/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/form[1]/div[3]/button[1] | //body/section[@id='login']/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/form[1]/div[4]/button[1]"));
            loginButton.click();
            Thread.sleep(3000);

			try {
				Thread.sleep(2000);
				WebElement account = driver.findElement(By.xpath("//body[1]/section[1]/div[1]/div[1]/div[1]/aside[1]/div[4]/a[1]/span[1]"));
				if (account.isEnabled()) {
					System.out.println("Login successfully!");                   

					Thread.sleep(2000);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("scroll(0,900)");
					Thread.sleep(1000);

					// Click on logout button
					driver.findElement(By.xpath("//body[1]/section[1]/div[1]/div[1]/div[1]/aside[1]/div[4]/a[1]/span[1]")).click();
					sheet.getRow(i).createCell(4).setCellValue("PASS");

					Thread.sleep(2000);
					driver.navigate().to("https://shop.timexindia.com/account/login?return_url=%2Faccount#passwordlogin");
					Thread.sleep(2000);
				} else {
					System.out.println("Login failed!");
					sheet.getRow(i).createCell(4).setCellValue("FAIL");
				}
			} catch (Exception e) {
				System.out.println("Error occurred during login!");
				sheet.getRow(i).createCell(4).setCellValue("FAIL");
				e.printStackTrace();
			}


			// Continue loop
			continue;
		}	

		Thread.sleep(1000);
		fileOut = new FileOutputStream("C:\\Users\\LYXELANDFLAMINGO\\Desktop\\Timex\\Timex India.xlsx");
		workbook.write(fileOut);
		fileOut.close();
	}
	@AfterTest
	public void quitBrowser() {
		driver.quit();
	}
}