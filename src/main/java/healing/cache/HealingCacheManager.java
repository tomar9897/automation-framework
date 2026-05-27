package healing.cache;

import healing.models.HealingResult;
import healing.persistence.HealingCachePersistenceManager;
import healing.reporting.HealingExtentReporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HealingCacheManager {

	private static final Map<String, HealingResult> healingCache = HealingCachePersistenceManager.loadCache();

    public static void saveHealingResult(HealingResult result) {
        healingCache.put(result.getOldLocator(), result);
        HealingCachePersistenceManager.saveCache(healingCache);
        System.out.println("Healing cache updated for locator: " + result.getOldLocator());
    }

    public static HealingResult getHealingResult(String originalLocator) throws IOException {
        HealingResult result = healingCache.get(originalLocator);
        if (result != null) {
            System.out.println("Using cached healed locator: " + result.getNewLocator());
            HealingExtentReporter.logCacheHit(originalLocator, result.getNewLocator());
        }
        return result;
    }

    public static boolean hasHealingResult(String originalLocator) {
        return healingCache.containsKey(originalLocator);
    }
}