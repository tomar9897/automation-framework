package healing.reporting;

public class HealingAnalyticsManager {

    private static int totalHealingAttempts = 0;
    private static int successfulHealings = 0;
    private static int failedHealings = 0;

    public static void recordSuccess() {
        totalHealingAttempts++;
        successfulHealings++;
        printAnalytics();
    }

    public static void recordFailure() {
        totalHealingAttempts++;
        failedHealings++;
        printAnalytics();
    }

    private static void printAnalytics() {
        double successRate = totalHealingAttempts == 0
                ? 0 : ((double) successfulHealings / totalHealingAttempts) * 100;
        System.out.println("\n========== HEALING ANALYTICS ==========");
        System.out.println("Total Attempts      : " + totalHealingAttempts);
        System.out.println("Successful Healings : " + successfulHealings);
        System.out.println("Failed Healings     : " + failedHealings);
        System.out.println("Success Rate        : " + successRate + "%");
        System.out.println("=======================================\n");
    }
}