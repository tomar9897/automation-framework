package healing.reporting;

import java.io.FileWriter;
import java.io.IOException;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import healing.utils.ScreenshotUtils;

public class HealingReportManager {

    private static final String REPORT_PATH = "target/healing-report.txt";

    public static void saveReport(HealingReportEntry entry) {
        try (FileWriter writer = new FileWriter(REPORT_PATH, true)) {
            writer.write(entry.formatReport());
            System.out.println("Healing report updated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}