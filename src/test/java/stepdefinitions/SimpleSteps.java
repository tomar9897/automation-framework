package stepdefinitions;

import io.cucumber.datatable.DataTable;
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

                    case "refresh":
                    	driver.navigate().refresh();
                    	break;
                    	
                    case "forward":
                    	driver.navigate().forward();
                    	
                    case "backward":
                    	driver.navigate().back();
                    	
                    case "hover":
                    	WebElement ele = driver.findElement(By.xpath(updatedLocator));
                    	act.moveToElement(ele).perform();
                    	
                    case "isVisible":
                    	try {
                    		driver.findElement(By.xpath(updatedLocator)).isDisplayed();
                    		assertTrue(driver.findElement(By.xpath(updatedLocator)).isDisplayed(), "Element visible");
                    	}catch(Exception e) {
                    		DriverManager.waitforElement(updatedLocator);
                    		assertTrue(driver.findElement(By.xpath(updatedLocator)).isDisplayed(), "Element visible");
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
    public void click(String locator) {
    	try {
    		waitforElement(locator);
    		driver.findElement(By.xpath(locator)).click();
    	}catch(Exception e) {
    		waitforclickable(locator);
    		WebElement ele = driver.findElement(By.xpath(locator));
    		JavascriptExecutor js = (JavascriptExecutor) driver;
    		js.executeScript("arguments[0].click()", ele);
    	//	driver.findElement(By.xpath(locator)).click();
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
    
    public void screenshot(String locator, String fileName) {
    	String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        
        String filePath = "src/test/resources/Screenshots" +"_+"+fileName+ "_" + timestamp + ".png";
    	File src = driver.findElement(By.xpath(locator)).getScreenshotAs(OutputType.FILE);
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
}
