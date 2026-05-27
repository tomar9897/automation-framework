package healing.models;

import org.openqa.selenium.WebElement;

public class HealingCandidate {

    private WebElement element;
    private double score;
    private String matchedBy;

    public HealingCandidate(WebElement element,
                            double score,
                            String matchedBy) {

        this.element = element;
        this.score = score;
        this.matchedBy = matchedBy;
        
    }

    public WebElement getElement() {
        return element;
    }

    public double getScore() {
        return score;
    }

    public String getMatchedBy() {
        return matchedBy;
    }
}