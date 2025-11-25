package Form_testing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class Practice1 {

	public static void main(String[] args) {
		
		WebDriver driver;
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("https://shop.timexindia.com/");
		
		WebElement abcd= driver.findElement(By.xpath("//boewrtrgbfddy[1]/div[4]/section[1]/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/a[1]"));
		abcd.isDisplayed();
		System.out.println(abcd);
		
		
		//List<WebElement> xpath = driver.findElements(By.xpath(null));
		//List<WebElement> links=driver.findElements(By.xpath("//button"));
		
		//for(WebElement link : links) 
	//	{
			
	//		System.out.println(link.getText());
	//	}
		

	}

}
