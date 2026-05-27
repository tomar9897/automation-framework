package healing.utils;

public class SimilarityUtils {

    public static int levenshteinDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costs = new int[s2.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= s1.length(); i++) {
            costs[0] = i;
            int northwest = i - 1;
            for (int j = 1; j <= s2.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), s1.charAt(i - 1) == s2.charAt(j - 1) ? northwest : northwest + 1);
                northwest = costs[j];
                costs[j] = cj;
            }
        }
        return costs[s2.length()];
    }

    public static double similarityScore(String s1, String s2) {
        if (s1.isEmpty() || s2.isEmpty()) return 0;
        int distance = levenshteinDistance(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());
        return (1.0 - ((double) distance / maxLength)) * 100;
    }
}