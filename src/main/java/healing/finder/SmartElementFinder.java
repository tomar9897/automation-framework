package healing.finder;

import healing.engine.HealingEngine;
import healing.cache.HealingCacheManager;
import healing.models.HealingResult;

import java.io.IOException;

import org.openqa.selenium.*;

public class SmartElementFinder {

    private WebDriver driver;
    private HealingEngine healingEngine;

    public SmartElementFinder(WebDriver driver) {

        this.driver = driver;
        this.healingEngine =
                new HealingEngine(driver);
    }

    public WebElement findElement(By locator) throws IOException {
        try {
            return driver.findElement(locator);
        } catch (NoSuchElementException e) {
            System.out.println("Original locator failed: " + locator);
            // CHECK CACHE FIRST
            if (HealingCacheManager.hasHealingResult(locator.toString())) {
                HealingResult cachedResult = HealingCacheManager.getHealingResult(locator.toString());
                System.out.println("Using cached healed locator: " + cachedResult.getNewLocator());
                return driver.findElement(By.xpath(cachedResult.getNewLocator()));
            }
            // RUN HEALING ENGINE
            return healingEngine.healElement(locator);
        }
    }
}