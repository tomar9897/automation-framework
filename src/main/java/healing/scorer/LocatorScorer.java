package healing.scorer;

import org.openqa.selenium.WebElement;
import healing.utils.SimilarityUtils;
import healing.context.DOMContextAnalyzer;

public class LocatorScorer {

	public double calculateScore(WebElement element,String tag,String attribute,String attributeValue) {
        double score = 0.0;
        try {
            tag = safeValue(tag);
            attribute = safeValue(attribute);
            attributeValue = safeValue(attributeValue);
            String elementTag = safeValue(element.getTagName());
            

            // TAG match
            if (elementTag.equals(tag)) {
                score += 40;
            } else {
                score -= 20;
            }

            // ATTRIBUTE match
            String actualAttributeValue =
                    safeValue(
                            element.getAttribute(attribute)
                    );

            if (!actualAttributeValue.isEmpty()) {
                // Exact match
            	if (actualAttributeValue.equals(attributeValue)) {
            	    score += 100 + getAttributeWeight(attribute);
            	}
                // Partial containment match
                else if (actualAttributeValue.contains(attributeValue) || attributeValue.contains(actualAttributeValue)) {
                    // safer handling for short valid attributes
                    if (actualAttributeValue.length() <= 3) {
                    	// reject suspicious numeric-heavy locators
                        if (attributeValue.matches(".*\\d{3,}.*")) {
                        	score += 0;
                        }
                        else {
                        	score += 45;
                        }
                    } else {
                        double similarity = SimilarityUtils.similarityScore(actualAttributeValue, attributeValue);
                        if (similarity >= 40) score += 50 + getAttributeWeight(attribute);
                    }
                }
                // Fuzzy similarity match
                else {
                    double similarity = SimilarityUtils.similarityScore(actualAttributeValue, attributeValue);
                    if (similarity >= 50) score += similarity + getAttributeWeight(attribute);
                }
            }
         score += compareAttribute(element.getAttribute("placeholder"), attributeValue, 15);

         // NAME similarity
         score += compareAttribute(element.getAttribute("name"), attributeValue, 15);

         // CLASS similarity
         score += compareAttribute(element.getAttribute("class"), attributeValue, 10);

         // TYPE similarity
         score += compareAttribute(element.getAttribute("type"), attributeValue, 10);
      // CONTEXT AWARENESS
         score += DOMContextAnalyzer.analyzeContext(element, attributeValue);

            // TEXT fallback
            String text =
                    safeValue(element.getText());

            if (!text.isEmpty()
                    && text.contains(attributeValue)) {

                score += 20;
            }
            

            if (score > 0) {

                System.out.println(
                        "Matched candidate -> "
                                + elementTag
                                + " | score = "
                                + score
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return score;
    }

    private String safeValue(String value) {
        return value == null ? "" : value.toLowerCase();
    }
    
    private double getAttributeWeight(String attribute) {
        attribute = safeValue(attribute);
        switch (attribute) {
            case "id": return 40;
            case "data-testid": return 35;
            case "name": return 30;
            case "aria-label":
                return 25;
            case "placeholder":
                return 20;
            case "type":
                return 15;
            case "class":
                return 5;
            case "text":
                return 3;
            default:
                return 10;
        }
    }
    
    private double compareAttribute(String candidateValue, String originalValue, double weight) {
        candidateValue = safeValue(candidateValue);
        originalValue = safeValue(originalValue);
        if (candidateValue.isEmpty() || originalValue.isEmpty()) return 0;
        if (candidateValue.equals(originalValue)) return weight;
        double similarity = SimilarityUtils.similarityScore(candidateValue, originalValue);
        if (similarity >= 50) return (similarity / 100.0) * weight;
        return 0;
    }
    
}