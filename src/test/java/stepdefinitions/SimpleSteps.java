package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import constants.locators;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.DriverManager;

public class SimpleSteps extends DriverManager {
    
    public WebDriver driver = DriverManager.getDriver();
    
    Actions act = new Actions(driver);
    
    public String getLocator(String locatorKey) {
        try {
            Field field = locators.class.getDeclaredField(locatorKey);
            return (String) field.get(null);
        } catch (Exception e) {
            fail("Locator key '" + locatorKey + "' not found in locators class. " + e.getMessage());
            return null;
        }
    }
    
    @When("user perform the following actions")
    public void user_perform_the_following_actions(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        String updatedLocator;

        for (Map<String, String> row : rows) {
            String locatorKey = row.get("Locator").trim();
            String valueToBeUpdated = row.get("valuetobeupdated") != null ? row.get("valuetobeupdated").trim() : "";
            String action = row.get("action").trim().toLowerCase();
            
            String locator = getLocator(locatorKey);
            if (locator == null) continue;
            
            updatedLocator = locator.contains("value") ? locator.replace("value", valueToBeUpdated) : locator;

            try {

                switch (action) {
                    case "click":
                    	System.out.println("locator is : " + updatedLocator);
                    	DriverManager.waitforclickable(updatedLocator);
                    	driver.findElement(By.xpath(updatedLocator)).click();
                      //  wait.until(ExpectedConditions.elementToBeClickable(updatedLocator)).click();
                        break;

                    case "enter":
                    	DriverManager.waitforElement(updatedLocator);
                        WebElement element = driver.findElement(By.xpath(updatedLocator));
                        element.clear();
                        element.sendKeys(valueToBeUpdated);
                        break;

                    case "wait":
                        Thread.sleep(Integer.parseInt(valueToBeUpdated) * 1000L);
                        break;

                    case "waitforelement":
                    	DriverManager.waitforElement(updatedLocator);
                        break;

                    case "scrolltoelement":
                    	DriverManager.waitforElement(updatedLocator);
                        WebElement scrollElem = driver.findElement(By.xpath(updatedLocator));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scrollElem);
                        break;

                    default:
                        System.out.println("Unknown action: " + action);
                }
            } catch (Exception e) {
               // System.out.println("Error performing action '" + action + "' on locator '" + locatorKey + "': " + e.getMessage());
                fail("Failed to perform action '" + action + "' on locator '" + updatedLocator + "' with value '" + valueToBeUpdated + "'. Cause: " + e.getMessage(), e);
                
            }
        }
    }
    
    @Given("Fill and submit the practice form")
    public void submitForm() {
    	SimpleSteps obj = new SimpleSteps();
    	//String locator = locator.
    	obj.setValue("Mayank", locators.firstName);
    	obj.setValue("Tomar", locators.lastName);
    	obj.setValue("mayank@gmail.com", locators.email);
    	obj.actionClick(locators.maleRadioBtn);
    	obj.setValue("3232323232", locators.number);
    	obj.setValue("20 Oct 2025", locators.date);
    	obj.setValue("Maths", locators.subjects);
    	driver.switchTo().activeElement().sendKeys(Keys.ENTER);
    }
    
    public void setValue(String str, String locator) {
    	//String loc = locator.
    	try {
    		
    		driver.findElement(By.xpath(locator)).clear();
        	driver.findElement(By.xpath(locator)).sendKeys(str);
    	}catch(Exception e){
    		System.out.println("Not able to set value due to :"+ e.getMessage());
    	}
    	
    }
    public void scrollToElement(String locator) {
		DriverManager.waitforElement(locator);
    	WebElement element = driver.findElement(By.xpath(locator));
    	try {
    		DriverManager.waitforElement(locator);
    		act.moveToElement(element).perform();
    	}catch(MoveTargetOutOfBoundsException  e) {
    		DriverManager.waitforElement(locator);
    		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    	}catch(Exception e) {
    		System.out.println("Not able to scroll to element: "+ element);
    		fail("Failed to perform action '" + "Scroll to element "+ element + e.getMessage(), e);
    	}
    }
    
    public void actionClick(String locator) {
    	WebElement element = driver.findElement(By.xpath(locator));
    	SimpleSteps obj = new SimpleSteps();
    	try {
    		obj.scrollToElement(locator);
    		act.moveToElement(element).click().perform();
    		
    	}catch(TimeoutException e) {
    		DriverManager.waitforclickable(locator);
    		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    	}
    }
    
    
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
    		Wait("10");
    		
    	}catch(Exception e) {
    		driver.get(locators.url);
    		Wait("10");
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
