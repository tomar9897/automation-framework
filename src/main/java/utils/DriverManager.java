package utils;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverManager {
    private static WebDriver driver;
    
    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }
    
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    
    public static void waitToLoadCompletely() {
    	new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
    }
    
    public static void Wait(String wait) throws MalformedURLException, IOException {
    	try {
            Thread.sleep(Integer.valueOf(wait) * 1000);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    	
    	
    }
    
    public static void waitforElement(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        
    }
    
    public static void waitforclickable(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
        
    }
}
