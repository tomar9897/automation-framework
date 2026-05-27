package utils;

import healing.finder.SmartElementFinder;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverManager {

    private static WebDriver driver;

    // SELF-HEALING FINDER
    private static SmartElementFinder smartFinder;

    public static WebDriver getDriver() {

        if (driver == null) {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            // AWS / EC2 / Jenkins SAFE
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");

            // Existing main branch setting
            options.addArguments("--remote-allow-origins=*");

            // Optional
            options.addArguments("--window-size=1920,1080");

            driver = new ChromeDriver(options);

            // INITIALIZE HEALING ENGINE
            smartFinder = new SmartElementFinder(driver);

            driver.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(10));
        }

        return driver;
    }

    // GET SMART FINDER
    public static SmartElementFinder getSmartFinder() {
        return smartFinder;
    }

    public static void quitDriver() {

        if (driver != null) {

            driver.quit();

            driver = null;
            smartFinder = null;
        }
    }

    public static void waitToLoadCompletely() {

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(driver ->
                        ((JavascriptExecutor) driver)
                                .executeScript("return document.readyState")
                                .equals("complete"));
    }

    public static void Wait(String wait)
            throws MalformedURLException, IOException {

        try {

            Thread.sleep(Integer.parseInt(wait) * 1000L);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }

    // UPDATED WITH SELF-HEALING
    public static WebElement findElement(String xpath) throws IOException {

        return smartFinder.findElement(By.xpath(xpath));
    }

    public static void waitforElement(String locator) {

        WebDriverWait wait =
                new WebDriverWait(driver,
                        Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(locator)
                )
        );
    }

    public static void waitforclickable(String locator) {

        WebDriverWait wait =
                new WebDriverWait(driver,
                        Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(locator)
                )
        );
    }
}