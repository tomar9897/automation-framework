package healing.context;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DOMContextAnalyzer {

    public static double analyzeContext(WebElement element, String expectedValue) {
        double contextScore = 0.0;
        try {
            expectedValue = safeValue(expectedValue);
            // CHECK LABEL TEXT
            try {
                WebElement label = element.findElement(By.xpath("./preceding::label[1]"));
                String labelText = safeValue(label.getText());
                if(!labelText.isEmpty()
                        && !expectedValue.isEmpty()
                        && (labelText.contains(expectedValue)
                        || expectedValue.contains(labelText))) {
                    contextScore += 30;
                    System.out.println("Context match via LABEL -> " + labelText);
                }
            } catch (Exception ignored) {}

            // CHECK PLACEHOLDER
            String placeholder = safeValue(element.getAttribute("placeholder"));
            if(!placeholder.isEmpty()
                    && !expectedValue.isEmpty()
                    && (placeholder.contains(expectedValue)
                    || expectedValue.contains(placeholder))) {

                contextScore += 20;
                System.out.println( "Context match via PLACEHOLDER -> "+ placeholder);
            }

            // CHECK ARIA LABEL
            String ariaLabel = safeValue(element.getAttribute("aria-label"));
            if(!ariaLabel.isEmpty()
                    && !expectedValue.isEmpty()
                    && (ariaLabel.contains(expectedValue)
                    || expectedValue.contains(ariaLabel))) {

                contextScore += 20;

                System.out.println(
                        "Context match via ARIA-LABEL -> "
                                + ariaLabel
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contextScore;
    }

    private static String safeValue(String value) {
        return value == null ? "" : value.toLowerCase();
    }
}