package healing.threshold;

public class HealingThresholdManager {

    public static double getThreshold(String tag) {

        tag = tag.toLowerCase();

        switch (tag) {

            case "input":
                return 50;

            case "textarea":
                return 55;

            case "button":
                return 75;

            case "a":
                return 80;

            case "div":
                return 85;

            default:
                return 70;
        }
    }
}