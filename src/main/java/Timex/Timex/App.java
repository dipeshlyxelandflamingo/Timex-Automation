package Timex.Timex;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class App 
{
	WebDriver driver;
    FileInputStream file;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    DataFormatter formatter;    
    FileOutputStream fileOut;

    @BeforeTest
    void setup() throws Throwable {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Load Excel file
        file = new FileInputStream("C:\\Users\\LYXELANDFLAMINGO\\Desktop\\Timex\\Timex India.xlsx");
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheet("Complete Flow ");
        formatter = new DataFormatter();
    }

    @Test(description = "TC_01_openbrowser")
    void TC_01_openbrowser() throws Throwable {                
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("1");
        row.createCell(1).setCellValue("TC 01");
        row.createCell(2).setCellValue("openbrowser");

        driver.get("https://shop.timexindia.com/");

        try {
            row.createCell(3).setCellValue("PASS");        
        } catch(Exception e) {            
            row.createCell(3).setCellValue("FAIL");
        }            
    }
    
    @Test(description = "TC_02_closepopup")
    void TC_02_closepopup() throws Throwable {
        Row row = sheet.createRow(2);
        row.createCell(0).setCellValue("2");
        row.createCell(1).setCellValue("TC 02");
        row.createCell(2).setCellValue("Closed Pop Up");
        
        Thread.sleep(4000);
        driver.findElement(By.xpath("//span[contains(text(),'×')]")).click();            

        try {
            row.createCell(3).setCellValue("PASS");        
        } catch(Exception e) {            
            row.createCell(3).setCellValue("FAIL");
        }    
    }
        

    @Test(description = "TC_06 ")
    void TC_06_() throws Throwable {          

       
    	Row row = sheet.createRow(8);
		row.createCell(0).setCellValue("8");
		row.createCell(1).setCellValue("TC 08");
		row.createCell(2).setCellValue("click on Popular Product list");

		driver.findElement(By.xpath("//a[normalize-space()='Best Sellers']")).click();
		Thread.sleep(2000);          
		driver.navigate().back();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//a[normalize-space()='Automatic']")).click();
		Thread.sleep(2000);          
		driver.navigate().back();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//a[normalize-space()='Leather']")).click();
		Thread.sleep(2000);          
		driver.navigate().back();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//a[normalize-space()='Digital']")).click();
		Thread.sleep(2000);          
		driver.navigate().back();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//a[normalize-space()='Chronograph']")).click();
		Thread.sleep(2000);          
		driver.navigate().back();
		Thread.sleep(3000);

		try {
			row.createCell(3).setCellValue("PASS");        
		} catch(Exception e) {            
			row.createCell(3).setCellValue("FAIL");
		}          

	}

    	
    
        
        @AfterTest
        void quit() throws Exception {
            Thread.sleep(2000);
            driver.quit();
            fileOut = new FileOutputStream("C:\\Users\\LYXELANDFLAMINGO\\Desktop\\Timex\\Timex India.xlsx");
            workbook.write(fileOut);

            // Close resources
            workbook.close();
            fileOut.close();
            file.close();    
        }
    }

