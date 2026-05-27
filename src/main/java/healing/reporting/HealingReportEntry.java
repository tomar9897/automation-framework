package healing.reporting;

public class HealingReportEntry {

    private String timestamp;
    private String status;
    private String originalLocator;
    private String healedLocator;
    private double confidenceScore;
    private String pageUrl;

    public HealingReportEntry(String timestamp, String status, String originalLocator,
                               String healedLocator, double confidenceScore, String pageUrl) {
        this.timestamp = timestamp;
        this.status = status;
        this.originalLocator = originalLocator;
        this.healedLocator = healedLocator;
        this.confidenceScore = confidenceScore;
        this.pageUrl = pageUrl;
    }

    public String formatReport() {
        return "\n====================================\n"
                + "Timestamp        : " + timestamp + "\n"
                + "Status           : " + status + "\n"
                + "Original Locator : " + originalLocator + "\n"
                + "Healed Locator   : " + healedLocator + "\n"
                + "Confidence Score : " + confidenceScore + "\n"
                + "Page URL         : " + pageUrl + "\n"
                + "====================================\n";
    }
}