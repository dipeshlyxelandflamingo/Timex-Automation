package Form_testing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Sign_In_by_Mobile {
	
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
		sheet = workbook.getSheet("Login via Mobile");
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
		driver.navigate().to("https://shop.timexindia.com/account/login?return_url=%2Faccount");		

	}

	@Test(description = "TC_03_Login")
	void TC_03_Login() throws Throwable {
		for (int i = 1; i <= 8; i++) {
			cell = sheet.getRow(i).getCell(1);
			String mobile = formatter.formatCellValue(cell);

			WebElement mobilefield = driver.findElement(By.id("phoneNumberInput"));
			
			mobilefield.clear();

			mobilefield.sendKeys(mobile);
			
			
			
			Thread.sleep(2000);
			driver.findElement(By.id("getOTPButton")).click();
			
			System.out.println("User" + "=" + i);
			System.out.println("first_name" + "=" + mobile);
			
			
			try {
				WebElement otpmsg = driver.findElement(By.id("loginSuccess"));
				if (otpmsg.isDisplayed()) {
					System.out.println("OTP Recieved successfully!");
					sheet.getRow(i).createCell(3).setCellValue("PASS");
					driver.navigate().to("https://shop.timexindia.com/account/login?return_url=%2Faccount");
					Thread.sleep(2000);

				} else {
					System.out.println("OTP Not Recieved!");
					sheet.getRow(i).createCell(3).setCellValue("FAIL");
				}
			} catch (Exception e) {
				System.out.println("OTP Not Recieved!");
				sheet.getRow(i).createCell(3).setCellValue("FAIL");
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
			
	