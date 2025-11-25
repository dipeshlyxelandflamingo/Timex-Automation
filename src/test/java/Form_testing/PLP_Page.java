package Form_testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseClass;

public class PLP_Page extends BaseClass{

	@Test
	public void TC_01_SelectMenFilter() {
		
		
		WebElement MensFilter = driver.findElement(By.xpath("//button[@title='Men (528)']"));
		
		MensFilter.click();
		
		 String selectedClass = MensFilter.getAttribute("class");
		
		 Assert.assertTrue(selectedClass.contains("active") || selectedClass.contains("selected"),
		            "❌ Men filter is NOT selected");
		    
		    System.out.println("✔ Men filter is selected");
	}
}
