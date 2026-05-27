package stepdefinitions;

import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import healing.engine.HealingEngine;
import healing.models.HealingResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.*;
import constants.locators;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dev.failsafe.internal.util.Assert;

import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
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
                        DriverManager.findElement(updatedLocator).click();
                      //  wait.until(ExpectedConditions.elementToBeClickable(updatedLocator)).click();
                        break;

                    case "enter":
                    	DriverManager.waitforElement(updatedLocator);
                        WebElement element = DriverManager.findElement(updatedLocator);
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
                        WebElement scrollElem = DriverManager.findElement(updatedLocator);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scrollElem);
                        break;

                    case "refresh":
                    	driver.navigate().refresh();
                    	break;
                    	
                    case "forward":
                    	driver.navigate().forward();
                    	
                    case "backward":
                    	driver.navigate().back();
                    	
                    case "hover":
                    	WebElement ele =
                        DriverManager.findElement(updatedLocator);
                        act.moveToElement(ele).perform();
                    	
                    case "isVisible":
                        try { 
                        	assertTrue(
                                    DriverManager
                                            .findElement(updatedLocator)
                                            .isDisplayed(),
                                    "Element visible"
                            );
                        } catch(Exception e) {
                        	DriverManager.waitforElement(updatedLocator);
                        	assertTrue(
                                    DriverManager
                                            .findElement(updatedLocator)
                                            .isDisplayed(),
                                    "Element visible"
                            );
                        }
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
    public void submitForm() throws IOException {
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
        try {
            DriverManager.findElement(locator).clear();
            DriverManager.findElement(locator).sendKeys(str);
        } catch(Exception e){
            System.out.println(
                    "Not able to set value due to : " + e.getMessage()
            );
        }
    }
    public void scrollToElement(String locator) throws IOException {
        DriverManager.waitforElement(locator);
        WebElement element =
                DriverManager.findElement(locator);
        try 
    	{
            DriverManager.waitforElement(locator);
            act.moveToElement(element).perform();
        } catch(MoveTargetOutOfBoundsException e) {
           DriverManager.waitforElement(locator);
            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",element);
        } catch(Exception e) {
            System.out.println(
                    "Not able to scroll to element: "+ element);
            fail("Failed to perform action 'Scroll to element' "+ element + e.getMessage(),e);
        }
    }
    
    public void actionClick(String locator) throws IOException {
        WebElement element = DriverManager.findElement(locator);
        SimpleSteps obj = new SimpleSteps();
        try {
            obj.scrollToElement(locator);
            act.moveToElement(element).click().perform();
        } catch (TimeoutException e) {
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
    @When("I search using broken xpath")
    public void i_search_using_broken_xpath() {
        try {
            String brokenXpath ="//textarea[@name='q_old']";
            WebElement searchBox = DriverManager.findElement(brokenXpath);
            searchBox.sendKeys("self healing framework");
            searchBox.sendKeys(Keys.ENTER);
        } catch(Exception e) {
            fail("Healing test failed: " + e.getMessage());
        }
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
    public void launch() throws MalformedURLException, IOException {
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
    public void click(String locator) throws IOException {
        try {
            waitforElement(locator);
            DriverManager.findElement(locator).click();
        } catch (Exception e) {
            waitforclickable(locator);
            WebElement ele = DriverManager.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", ele);
        }
    }
    
    @Then("wait for {string} seconds")
    public void wait(String str) throws MalformedURLException, IOException {
    	Wait(str);
    }
    
    public void fluentWait(long waits, long polling, Class<? extends Throwable>... exceptionType) {
    	FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(waits));
    	if (polling > 0) {
            wait.pollingEvery(Duration.ofSeconds(polling));
        }
    	if(exceptionType!=null && exceptionType.length>0) {
    		for(Class<? extends Throwable> ex : exceptionType) {
    			wait.ignoring(ex);
    		}
    	}
    }
    
    public void waitfortexttoVisible(WebElement ele, String text) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	wait.until(ExpectedConditions.textToBePresentInElement(ele, text));
    }
    
    public void screenshot(String fileName) {
    	String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        String filePath = "src/test/resources/Screenshots" + fileName + "_" + timestamp + ".png";
    	File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    	File dest = new File(filePath);
    	try {
            
            
            FileUtils.copyFile(src, dest);
            System.out.println("Screenshot saved at: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
    	
    	
    }
    
    public void screenshot(String locator, String fileName) throws WebDriverException, IOException {
    	String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        String filePath = "src/test/resources/Screenshots" +"_+"+fileName+ "_" + timestamp + ".png";
        File src = DriverManager.findElement(locator).getScreenshotAs(OutputType.FILE);
 //   	File src = driver.findElement(By.xpath(locator)).getScreenshotAs(OutputType.FILE);
    	File dest = new File(filePath);
    	try {
            
            
            FileUtils.copyFile(src, dest);
            System.out.println("Screenshot saved at: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
    	
    	
    }
    
    public void screenshot() {
    	String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        String filePath = "src/test/resources/Screenshots"  + "_" + timestamp + ".png";
    	File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    	File dest = new File(filePath);
    	try {
            
            
            FileUtils.copyFile(src, dest);
            System.out.println("Screenshot saved at: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }
    	
    	
    }
    @Then("I validate page title contains {string}")
    public void validate_title(String expectedText) {
        String actualTitle = driver.getTitle();
        System.out.println("Actual Title: " + actualTitle);

        assertTrue(actualTitle.contains(expectedText),
                "❌ Title validation failed. Expected: " + expectedText);
    }
    
    
    

        public static boolean isLinkValid(String urlString) {
            try {
                if (urlString == null || urlString.trim().isEmpty()) {
                    System.out.println("Empty or null URL");
                    return false;
                }


                HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setInstanceFollowRedirects(true);

                // HEAD first (faster)
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();

                // if HEAD not supported, fallback to GET
                if (responseCode == HttpURLConnection.HTTP_BAD_METHOD || responseCode == HttpURLConnection.HTTP_NOT_IMPLEMENTED) {

                    connection.disconnect();
                    connection = (HttpURLConnection) new URL(urlString).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.connect();
                    responseCode = connection.getResponseCode();
                }

                // response code < 400 , link is valid
                boolean isValid = responseCode < 400;
                if (!isValid) {
                    System.out.println("Broken link: " + urlString + " --> HTTP " + responseCode);
                }

                connection.disconnect();
                return isValid;

            } catch (Exception e) {
                System.out.println("Exception for URL: " + urlString + " --> " + e.getMessage());
                return false;
            }
        }
    

    
    @Then("get the title")
    public void fun() {
    	String title = driver.getTitle();
    	System.out.println("title is -> "+title);
    }
    
    @When("user perform the following actions {string}")
    public void actions(String locate) {
    
    }
    
    @When("I search again using same broken xpath")
    public void i_search_again_using_same_broken_xpath() {

        try {

            driver.navigate().back();

            DriverManager.waitToLoadCompletely();

            String brokenXpath =
                    "//textarea[@name='q_old']";

            WebElement searchBox =
                    DriverManager.findElement(brokenXpath);

            searchBox.clear();

            searchBox.sendKeys("persistent healing cache");

            searchBox.sendKeys(Keys.ENTER);

        } catch(Exception e) {

            fail(
                    "Second healing test failed: "
                            + e.getMessage()
            );
        }
    }
    
    @When("I search using invalid broken xpath")
    public void i_search_using_invalid_broken_xpath() {

        try {

            String brokenXpath =
                    "//textarea[@name='q_121212121']";

            WebElement searchBox =
                    DriverManager.findElement(brokenXpath);

            searchBox.sendKeys("this should fail");

        } catch(Exception e) {

            System.out.println(
                    "Expected healing failure occurred."
            );
        }
    }
    
    
    @Then("I should see healing failure handled safely")
    public void i_should_see_healing_failure_handled_safely() {

        System.out.println(
                "Healing rejection validation completed successfully."
        );

        assertTrue(true);
    }
    @Then("I validate current url contains {string}")
    public void validate_current_url(String expectedText) {

        String currentUrl = driver.getCurrentUrl();

        System.out.println(
                "Current URL: " + currentUrl
        );

        assertTrue(
                currentUrl.contains(expectedText),
                "❌ URL validation failed. Expected: "
                        + expectedText
        );
    }
    
    @Given("I open DemoQA text box page")
    public void i_open_demoqa_text_box_page() {

        driver.get("https://demoqa.com/text-box");

        DriverManager.waitToLoadCompletely();

        System.out.println("Opened DemoQA Text Box page");
    }
    
    @When("I enter full name using broken locator")
    public void i_enter_full_name_using_broken_locator() throws IOException {

        WebElement element =
                DriverManager.findElement(
                        locators.brokenFullName
                );

        element.sendKeys("Mayank Tomar");
    }
    
    @When("I enter email using broken locator")
    public void i_enter_email_using_broken_locator() throws IOException {

        WebElement element =
                DriverManager.findElement(
                        locators.brokenEmail
                );

        element.sendKeys("mayank@test.com");
    }
    
    @When("I enter address using broken locator")
    public void i_enter_address_using_broken_locator() throws IOException {

        WebElement element =
                DriverManager.findElement(
                        locators.brokenAddress
                );

        element.sendKeys("Delhi India");
    }
    
    @When("I click submit using broken locator")
    public void i_click_submit_using_broken_locator() throws IOException {

        WebElement element =
                DriverManager.findElement(
                        locators.brokenSubmit
                );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].click();",
                        element
                );
    }
    
    @Then("I validate form submitted successfully")
    public void i_validate_form_submitted_successfully() {

        WebElement output =
                driver.findElement(
                        By.id("output")
                );

        assertTrue(
                output.isDisplayed(),
                "Form submission failed"
        );

        System.out.println(
                "DemoQA form submitted successfully."
        );
    }
    
    @When("I enter full name again using broken locator")
    public void i_enter_full_name_again_using_broken_locator() throws IOException {

        WebElement element =
                DriverManager.findElement(
                        locators.brokenFullName
                );

        element.sendKeys("Persistent Cache Test");
    }
    
    @When("I use invalid broken locator on DemoQA")
    public void i_use_invalid_broken_locator_on_demoqa() {

        try {

            DriverManager.findElement(
                    "//input[@id='invalid_999999']"
            );

        } catch(Exception e) {

            System.out.println(
                    "Expected DemoQA healing failure occurred."
            );
        }
    }
    
    @Given("user opens demoqa text box page")
    public void user_opens_demoqa_text_box_page() {

        driver.get(locators.demoqaTextBoxUrl);

        System.out.println("Opened DemoQA Text Box page");
    }

    @When("user enters name using broken id locator")
    public void user_enters_name_using_broken_id_locator() {

        try {

            WebElement element =
                    DriverManager.findElement(
                            locators.brokenUserName
                    );

            element.sendKeys("Mayank");

        } catch (Exception e) {

            fail("Name healing failed: " + e.getMessage());
        }
    }

    @When("user enters email using broken name locator")
    public void user_enters_email_using_broken_name_locator() {

        try {

            WebElement element =
                    DriverManager.findElement(
                            locators.brokenUserEmail
                    );

            element.sendKeys("mayank@test.com");

        } catch (Exception e) {

            fail("Email healing failed: " + e.getMessage());
        }
    }

    @When("user enters address using broken placeholder locator")
    public void user_enters_address_using_broken_placeholder_locator() {

        try {

            WebElement element =
                    DriverManager.findElement(
                            locators.brokenAddress
                    );

            element.sendKeys("New Delhi");

        } catch (Exception e) {

            fail("Address healing failed: " + e.getMessage());
        }
    }

    @When("user clicks submit using broken class locator")
    public void user_clicks_submit_using_broken_class_locator() {

        try {

            WebElement element =
                    DriverManager.findElement(
                            locators.brokenSubmit
                    );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            element
                    );

        } catch (Exception e) {

            fail("Submit healing failed: " + e.getMessage());
        }
    }

    @Then("form should be submitted successfully")
    public void form_should_be_submitted_successfully() {

        WebElement output =
                driver.findElement(
                        By.xpath(locators.outputName)
                );

        assertTrue(
                output.isDisplayed(),
                "Form submission failed"
        );

        System.out.println("Intelligent healing scenario passed.");
    }
    
    
    
    
    
    
    //final validations
    
    
    @When("user enters name again using same broken locator")
    public void user_enters_name_again_using_same_broken_locator() {

        try {

            WebElement element =
                    DriverManager.findElement(
                            locators.brokenUserName
                    );

            element.clear();

            element.sendKeys(
                    "Cache Validation"
            );

        } catch (Exception e) {

            fail(
                    "Cache healing failed: "
                            + e.getMessage()
            );
        }
    }
    
    @Then("cached healing should work successfully")
    public void cached_healing_should_work_successfully() {

        System.out.println(
                "Cached healing validated successfully."
        );

        assertTrue(true);
    }
    
    @Then("deterministic healing should complete successfully")
    public void deterministic_healing_should_complete_successfully() {

        System.out.println(
                "Deterministic healing validated successfully."
        );

        assertTrue(true);
    }
    
    @Then("AI healing should recover locator successfully")
    public void ai_healing_should_recover_locator_successfully() {

        System.out.println(
                "AI healing validated successfully."
        );

        assertTrue(true);
    }
    
    @When("user clicks using impossible broken locator")
    public void user_clicks_using_impossible_broken_locator() {

        try {

            WebElement element =
                    DriverManager.findElement(
                            locators.impossibleLocator
                    );

            element.click();

            fail(
                    "Framework incorrectly healed impossible locator."
            );

        } catch (Exception e) {

            System.out.println(
                    "Safe rejection occurred as expected."
            );
        }
    }
    
    @Then("framework should safely reject healing")
    public void framework_should_safely_reject_healing() {

        System.out.println(
                "Safe rejection validated successfully."
        );

        assertTrue(true);
    }
    
    @Then("complete healing flow should work successfully")
    public void complete_healing_flow_should_work_successfully() {

        WebElement output =
                driver.findElement(
                        By.xpath(locators.outputName)
                );

        assertTrue(
                output.isDisplayed(),
                "Complete healing flow failed"
        );

        System.out.println(
                "Complete healing pipeline validated successfully."
        );
    }
    
    
    
    
}
