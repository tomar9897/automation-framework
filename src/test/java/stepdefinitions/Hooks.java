package stepdefinitions;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverManager;

public class Hooks {
    
    @Before
    public void setUp() {
        System.out.println("Starting test...");
    }
    
    
    @AfterStep
    public void takeScreenshotAfterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot on Failure");
                System.out.println("Screenshot captured for failed step.");
            } catch (Exception e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                System.out.println("Scenario FAILED: " + scenario.getName());
                System.out.println("Failure Reason: " + scenario.getStatus());
                if (scenario.getSourceTagNames() != null) {
                    System.out.println("Tags: " + scenario.getSourceTagNames());
                }
            } else {
                System.out.println("Scenario PASSED: " + scenario.getName());
            }
        } catch (Exception e) {
            System.out.println("Error while handling scenario teardown: " + e.getMessage());
        } finally {
            DriverManager.quitDriver();
            System.out.println("Driver closed");
        }
    }
}

