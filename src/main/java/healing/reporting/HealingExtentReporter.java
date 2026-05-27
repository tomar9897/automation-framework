package healing.reporting;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.DriverManager;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import healing.models.HealingResult;
import healing.utils.ScreenshotUtils;

public class HealingExtentReporter {

	public static void logHealingSuccess(HealingResult result) {
	    try {
	        WebDriver driver = DriverManager.getDriver();
	        WebElement healedElement = driver.findElement(By.xpath(result.getNewLocator()));
	        String screenshotPath = ScreenshotUtils.captureHighlightedScreenshot("healing_success", healedElement);
	        String message = "<b style='color:green;'>SELF-HEALING SUCCESS</b><br><br>"
	                + "<b>Original Locator:</b> " + result.getOldLocator() + "<br>"
	                + "<b>Healed Locator:</b> " + result.getNewLocator() + "<br>"
	                + "<b>Confidence Score:</b> " + result.getConfidenceScore();
	        if (screenshotPath != null) {
	            message += "<br><br><a href='../screenshots/"
	                    + screenshotPath.substring(screenshotPath.lastIndexOf("/") + 1)
	                    + "' target='_blank'>View Screenshot</a>";
	        }
	        ExtentCucumberAdapter.addTestStepLog(message);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void logAIHealing(
	        String failedLocator,
	        String aiLocator) {

	    String message =
	            "<b style='color:purple;'>AI HEALING ACTIVATED</b><br><br>"
	            + "<b>Original Locator:</b> "
	            + failedLocator
	            + "<br>"
	            + "<b>AI Suggested Locator:</b> "
	            + aiLocator;

	    ExtentCucumberAdapter.addTestStepLog(message);
	}
	
	
	public static void logCacheHit(String originalLocator, String healedLocator) throws IOException {
	    String message = "<b style='color:blue;'>PERSISTENT CACHE HIT</b><br><br>"
	            + "<b>Original Locator:</b> " + originalLocator + "<br>"
	            + "<b>Cached Locator:</b> " + healedLocator;
	    ExtentCucumberAdapter.addTestStepLog(message);
	    String screenshotPath = ScreenshotUtils.captureScreenshot("cache_hit");
	    if (screenshotPath != null) {
	        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(screenshotPath);
	    }
	}

	public static void logHealingFailure(String locator, double confidenceScore) throws IOException {
	    String message = "<b style='color:red;'>SELF-HEALING FAILED</b><br><br>"
	            + "<b>Original Locator:</b> " + locator + "<br>"
	            + "<b>Reason:</b> Low confidence score<br>"
	            + "<b>Confidence Score:</b> " + confidenceScore;
	    ExtentCucumberAdapter.addTestStepLog(message);
	    String screenshotPath = ScreenshotUtils.captureScreenshot("healing_failure");
	    if (screenshotPath != null) {
	        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(screenshotPath);
	    }
	}
}