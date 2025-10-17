package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DriverManager;

public class Hooks {
    
    @Before
    public void setUp() {
        System.out.println("Starting test...");
    }
    
    
    @After
    public void tearDown() {
        DriverManager.quitDriver();
        System.out.println("Test finished.");
    }
}

