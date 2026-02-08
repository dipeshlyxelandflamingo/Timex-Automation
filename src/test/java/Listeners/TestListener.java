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
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // ✅ Track how many <test> blocks finished (so we flush once at end)
    private static int totalTestsInSuite = 0;
    private static int finishedTestsInSuite = 0;

    @Override
    public synchronized void onStart(ITestContext context) {

        // ✅ init only once
        if (extent == null) {
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

            // total <test> blocks in suite
            totalTestsInSuite = context.getSuite().getXmlSuite().getTests().size();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {

        String[] groups = result.getMethod().getGroups();
        String groupText = (groups != null && groups.length > 0)
                ? String.join(", ", groups)
                : "no-group";

        String testBlockName = result.getTestContext().getName();
        String testName = "[" + testBlockName + "] " + result.getMethod().getMethodName()
                + "  (Groups: " + groupText + ")";

        ExtentTest test = extent.createTest(testName);

        if (groups != null) {
            for (String g : groups) test.assignCategory(g);
        }

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
            if (driver != null) {
                driver.switchTo().defaultContent();
                String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

                extentTest.get().fail("Screenshot",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64,
                                result.getMethod().getMethodName()).build());
            } else {
                extentTest.get().warning("Driver was null, screenshot not captured.");
            }

        } catch (Exception e) {
            extentTest.get().warning("Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("Test Skipped");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        finishedTestsInSuite++;

        // ✅ flush only once at the very end (after all <test> blocks)
        if (finishedTestsInSuite >= totalTestsInSuite) {
            extent.flush();
        }
    }
}
