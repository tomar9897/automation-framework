package healing.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverManager;

public class ScreenshotUtils {

    public static String captureScreenshot(String screenshotName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destination = "target/screenshots/" + screenshotName + "_" + timestamp + ".png";
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);
            return destination;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String captureHighlightedScreenshot(String screenshotName, WebElement element) {
        try {
            WebDriver driver = DriverManager.getDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='3px solid red'", element);
            js.executeScript("arguments[0].style.background='yellow'", element);
            String screenshotPath = captureScreenshot(screenshotName);
            js.executeScript("arguments[0].style.border=''", element);
            js.executeScript("arguments[0].style.background=''", element);
            return screenshotPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}