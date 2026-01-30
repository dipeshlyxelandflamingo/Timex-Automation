package Listeners;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Base.BaseClass;

public class TestListener implements ITestListener {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {

		String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";

		ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		reporter.config().setReportName("Timex Automation Report");
		reporter.config().setDocumentTitle("Automation Execution Result");

		extent = new ExtentReports();
		extent.attachReporter(reporter);

		extent.setSystemInfo("Project", "Timex");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Executed By", "Automation");
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

	    extentTest.get().fail(result.getThrowable());

	    try {
	        WebDriver driver = ((BaseClass) result.getInstance()).driver;

	        // iframe se bahar aao (GoKwik fix)
	        driver.switchTo().defaultContent();

	        // ✅ Screenshot as BASE64 (NO file saving)
	        String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

	        // ✅ Attach directly in Extent report
	        extentTest.get().fail("Screenshot",
	                MediaEntityBuilder.createScreenCaptureFromBase64String(base64,
	                        result.getMethod().getMethodName()).build());

	    } catch (Exception e) {
	        e.printStackTrace();
	        extentTest.get().warning("Screenshot capture failed: " + e.getMessage());
	    }
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().skip("Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}