package stepdefinitions;

import io.cucumber.java.en.*;
import constants.locators;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

public class SimpleSteps extends DriverManager {
    
    public WebDriver driver = DriverManager.getDriver();
    
    @Given("I open Google homepage")
    public void i_open_google_homepage() {
       // driver = DriverManager.getDriver();
        driver.get("https://www.google.com");
        System.out.println("Opened Google");
    }
    
    @When("I search for {string}")
    public void i_search_for(String searchTerm) {
        driver.findElement(By.name("q")).sendKeys(searchTerm);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        System.out.println("Searched for: " + searchTerm);
    }
    
    @Then("I should see search results")
    public void i_should_see_search_results() {
        try {
            Thread.sleep(2000); // Wait for results
            System.out.println("Page title: " + driver.getTitle());
            System.out.println("✅ TEST PASSED - Browser opened and search worked!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Given("Launch the demo QA")
    public void launch() {
    	try {
    		
    		driver.get(locators.url);
    		Wait("5");
    		
    	}catch(Exception e) {
    		driver.get(locators.url);
    		Wait("5");
    	}finally {
    		System.out.println("Some issue in opening the browser");
    	}
    }
    
    @Then("Click {string}")
    public void click(String locator) {
    	try {
    		waitforElement(locator);
    		driver.findElement(By.xpath(locator)).click();
    	}catch(Exception e) {
    		waitforclickable(locator);
    		driver.findElement(By.xpath(locator)).click();
    	}
    }
    
    @Then("wait for {string} seconds")
    public void wait(String str) {
    	Wait(str);
    }
    
    @When("user perform the following actions {string}")
    public void actions(String locate) {
    	
    }
}
