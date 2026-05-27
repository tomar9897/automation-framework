package healing.models;

import org.openqa.selenium.WebElement;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class HealingResult {

    private boolean healed;
    @JsonIgnore
    private WebElement healedElement;
    private String oldLocator;
    private String newLocator;
    private double confidenceScore;

    // DEFAULT CONSTRUCTOR REQUIRED FOR JACKSON
    public HealingResult() {}

    public HealingResult(boolean healed, WebElement healedElement,
                         String oldLocator, String newLocator, double confidenceScore) {
        this.healed = healed;
        this.healedElement = healedElement;
        this.oldLocator = oldLocator;
        this.newLocator = newLocator;
        this.confidenceScore = confidenceScore;
    }

    public boolean isHealed() { return healed; }
    public void setHealed(boolean healed) { this.healed = healed; }

    public WebElement getHealedElement() { return healedElement; }
    public void setHealedElement(WebElement healedElement) { this.healedElement = healedElement; }

    public String getOldLocator() { return oldLocator; }
    public void setOldLocator(String oldLocator) { this.oldLocator = oldLocator; }

    public String getNewLocator() { return newLocator; }
    public void setNewLocator(String newLocator) { this.newLocator = newLocator; }

    public double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(double confidenceScore) { this.confidenceScore = confidenceScore; }
}