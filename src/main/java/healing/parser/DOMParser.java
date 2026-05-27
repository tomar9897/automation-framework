package healing.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import healing.models.LocatorMetadata;
import org.openqa.selenium.WebElement;

public class DOMParser {

    public String extractTag(String xpath) {

        Pattern pattern =
                Pattern.compile("//(\\w+)");

        Matcher matcher =
                pattern.matcher(xpath);

        if (matcher.find()) {

            return matcher.group(1);
        }

        return "";
    }

    public String extractAttribute(String xpath) {

        Pattern pattern =
                Pattern.compile("@(\\w+)");

        Matcher matcher =
                pattern.matcher(xpath);

        if (matcher.find()) {

            return matcher.group(1);
        }

        return "";
    }
    public LocatorMetadata buildMetadata(WebElement element) {
        return new LocatorMetadata(
                safeValue(element.getTagName()),
                safeValue(element.getAttribute("id")),
                safeValue(element.getAttribute("name")),
                safeValue(element.getAttribute("class")),
                safeValue(element.getAttribute("placeholder")),
                safeValue(element.getAttribute("type")),
                safeValue(element.getText())
        );
    }
    private String safeValue(String value) {
        return value == null ? "" : value.toLowerCase();
    }

    public String extractAttributeValue(String xpath) {

        Pattern pattern =
                Pattern.compile("'([^']*)'");

        Matcher matcher =
                pattern.matcher(xpath);

        if (matcher.find()) {

            return matcher.group(1);
        }

        return "";
    }
}