package healing.engine;

import healing.parser.DOMParser;
import healing.cache.HealingCacheManager;
import healing.models.HealingResult;
import healing.models.HealingCandidate;
import healing.scorer.LocatorScorer;
import healing.reporting.HealingReportEntry;
import healing.reporting.HealingReportManager;
import healing.reporting.HealingExtentReporter;
import healing.threshold.HealingThresholdManager;
import healing.reporting.HealingAnalyticsManager;
import healing.ai.AISuggestionEngine;
import healing.ai.DOMSnapshotBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

public class HealingEngine {

    private WebDriver driver;
    private LocatorScorer scorer;
    private DOMParser parser;

    public HealingEngine(WebDriver driver) {
    	this.parser = new DOMParser();
        this.driver = driver;
        this.scorer = new LocatorScorer();
    }

    public WebElement healElement(By failedLocator) throws IOException {

        System.out.println("Healing started for locator: " + failedLocator);

        String locatorValue = extractLocatorValue(failedLocator);
        String tag = parser.extractTag(locatorValue);
        String attribute = parser.extractAttribute(locatorValue);
        String attributeValue = parser.extractAttributeValue(locatorValue);
        System.out.println("Parsed XPath -> "+ "tag: " + tag + ", attribute: " + attribute + ", value: " + attributeValue
        );

        List<WebElement> allElements = driver.findElements(By.xpath("//" + tag));

        List<HealingCandidate> candidates = new ArrayList<>();

        for (WebElement element : allElements) {

            double score = scorer.calculateScore(element, tag, attribute, attributeValue);
            		//scorer.calculateScore(element, locatorValue);
//                    scorer.calculateScore(
//                            element,
//                            locatorValue
//                    );

            if (score > 0) {

                candidates.add(
                        new HealingCandidate(
                                element,
                                score,
                                "similarity-match"
                        )
                );
            }
        }

        HealingCandidate bestCandidate = getBestCandidate(candidates);
        double adaptiveThreshold = HealingThresholdManager.getThreshold(tag);
        System.out.println("Adaptive Threshold for tag [" + tag + "] : " + adaptiveThreshold);

        if (bestCandidate != null && bestCandidate.getScore() >= adaptiveThreshold) {
            String healedLocator = generateHealedXpath(bestCandidate);
            HealingResult result = new HealingResult(true, bestCandidate.getElement(),
                    failedLocator.toString(), healedLocator, bestCandidate.getScore());
            HealingCacheManager.saveHealingResult(result);
            HealingReportEntry reportEntry = new HealingReportEntry(LocalDateTime.now().toString(),
                    "SUCCESS", failedLocator.toString(), healedLocator,
                    bestCandidate.getScore(), driver.getCurrentUrl());
            HealingReportManager.saveReport(reportEntry);
            HealingExtentReporter.logHealingSuccess(result);
            HealingAnalyticsManager.recordSuccess();
            return bestCandidate.getElement();
        } else {
            System.out.println("Healing failed due to low confidence.");
            HealingReportEntry reportEntry = new HealingReportEntry(LocalDateTime.now().toString(),
                    "FAILED", failedLocator.toString(), "NO_HEALING_FOUND",
                    bestCandidate != null ? bestCandidate.getScore() : 0,
                    driver.getCurrentUrl());
            HealingReportManager.saveReport(reportEntry);
            HealingExtentReporter.logHealingFailure(failedLocator.toString(),
                    bestCandidate != null ? bestCandidate.getScore() : 0);
            HealingAnalyticsManager.recordFailure();
            String aiLocator = AISuggestionEngine.getSuggestedLocator(failedLocator.toString(), DOMSnapshotBuilder.buildSnapshot(driver));
            if (aiLocator != null) {

                if (aiLocator.equals("//button")
                        || aiLocator.equals("//div")
                        || aiLocator.equals("//span")
                        || aiLocator.equals("//input")
                        || aiLocator.equals("//textarea")
                        || aiLocator.equals("//nav/button")) {

                    System.out.println(
                            "Rejected generic AI locator: "
                                    + aiLocator
                    );

                } else {

                    try {

                        WebElement aiElement =
                                driver.findElement(
                                        By.xpath(aiLocator)
                                );

                        System.out.println(
                                "AI Suggested Locator Success -> "
                                        + aiLocator
                        );

                        HealingExtentReporter.logAIHealing(
                                failedLocator.toString(),
                                aiLocator
                        );

                        return aiElement;

                    } catch (Exception ignored) {
                    }
                }
            }
            throw new NoSuchElementException("No reliable healing candidate found for locator: " + failedLocator);
        }

//        throw new NoSuchElementException(
//                "Unable to heal locator: "
//                        + failedLocator
//        );
    }

    private HealingCandidate getBestCandidate(
            List<HealingCandidate> candidates) {

        HealingCandidate bestCandidate = null;

        for (HealingCandidate candidate : candidates) {

            if (bestCandidate == null ||
                    candidate.getScore()
                            > bestCandidate.getScore()) {

                bestCandidate = candidate;
            }
        }

        return bestCandidate;
    }
    
    private String generateHealedXpath(HealingCandidate candidate) {
        WebElement element = candidate.getElement();
        String tag = element.getTagName();
        String name = element.getAttribute("name");
        if (name != null && !name.isEmpty()) return "//" + tag + "[@name='" + name + "']";
        String id = element.getAttribute("id");
        if (id != null && !id.isEmpty()) return "//" + tag + "[@id='" + id + "']";
        return "//" + tag;
    }

    private String extractLocatorValue(By locator) {

        String locatorString = locator.toString();

        int index = locatorString.indexOf(":");
        if (index != -1) {
            return locatorString
                    .substring(index + 1)
                    .trim();
        }

        return locatorString;
    }
}