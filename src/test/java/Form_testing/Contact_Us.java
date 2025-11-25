package Form_testing;

import java.io.FileInputStream;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileOutputStream;
import java.time.Duration;
import org.openqa.selenium.support.ui.Select;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Contact_Us {

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
		sheet = workbook.getSheet("Contact Us ");
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
		driver.navigate().to("https://shop.timexindia.com/pages/contact");		

	}

	@Test(description = "TC_03_Login")
	void TC_03_Login() throws Throwable {
		for (int i = 1; i <= 14; i++) {
			cell = sheet.getRow(i).getCell(1);
			String FName = formatter.formatCellValue(cell);

			cell = sheet.getRow(i).getCell(2);
			String LName = formatter.formatCellValue(cell);			

			cell = sheet.getRow(i).getCell(3);
			String Email = formatter.formatCellValue(cell);

			cell = sheet.getRow(i).getCell(4);
			String Mobile = formatter.formatCellValue(cell);


			cell = sheet.getRow(i).getCell(6);
			String Comment = formatter.formatCellValue(cell);			


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

			//enter new value
			Firstname.sendKeys(FName);
			lastname.sendKeys(LName);
			EMail.sendKeys(Email);
			mobile.sendKeys(Mobile);

			WebElement reason = driver.findElement(By.id("reason"));
			Select tp = new Select(reason);
			tp.selectByValue("Other");

			comment.sendKeys(Comment);	

			//print the add value
			System.out.println("User" + "=" + i);
			System.out.println("first_name" + "=" + FName);
			System.out.println("last_name" + "=" + LName);
			System.out.println("Email" + "=" + Email);
			System.out.println("phone" + "=" + Mobile);
			System.out.println("Topic" + "=" + tp);
			System.out.println("comment" + "=" + Comment);	

			// Click the login button
			Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(0,500)");
			Thread.sleep(1000);;
			driver.findElement(By.id("contactSubmit")).click();;            
			Thread.sleep(2000);
			js.executeScript("scroll(0,-500)");
			Thread.sleep(2000);
			
			
			try {
				WebElement thankYouMessage = driver.findElement(By.xpath("//div[contains(text(),'Your message has been sent successfully.')]"));
				if (thankYouMessage.isDisplayed()) {
					System.out.println("Form submitted successfully!");
					sheet.getRow(i).createCell(8).setCellValue("PASS");
					driver.navigate().to("https://shop.timexindia.com/pages/contact");
					Thread.sleep(2000);

				} else {
					System.out.println("Form submission failed!");
					sheet.getRow(i).createCell(8).setCellValue("FAIL");
				}
			} catch (Exception e) {
				System.out.println("Form submission failed!");
				sheet.getRow(i).createCell(8).setCellValue("FAIL");
				e.printStackTrace();
				Thread.sleep(2000);
				continue;
			}




		}

		FileOutputStream fileOut = new FileOutputStream("C:\\Users\\LYXELANDFLAMINGO\\Desktop\\Timex\\Timex India.xlsx");
		workbook.write(fileOut);
		fileOut.close();
	}

	@AfterTest
	public void quitBrowser() {
		driver.quit();
	}
}