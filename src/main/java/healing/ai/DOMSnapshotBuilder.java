package healing.ai;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DOMSnapshotBuilder {

    public static String buildSnapshot(WebDriver driver) {

        StringBuilder snapshot = new StringBuilder();

        try {

            List<WebElement> elements =
                    driver.findElements(By.xpath("//*"));

            int limit =
                    Math.min(elements.size(), 100);

            for (int i = 0; i < limit; i++) {

                WebElement element =
                        elements.get(i);

                snapshot.append("<")
                        .append(element.getTagName());

                String id =
                        element.getAttribute("id");

                if (id != null && !id.isEmpty()) {

                    snapshot.append(" id='")
                            .append(id)
                            .append("'");
                }

                String name =
                        element.getAttribute("name");

                if (name != null && !name.isEmpty()) {

                    snapshot.append(" name='")
                            .append(name)
                            .append("'");
                }

                snapshot.append(">");

                snapshot.append("\n");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return snapshot.toString();
    }
}