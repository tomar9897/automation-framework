package healing.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import healing.models.HealingResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HealingCachePersistenceManager {

    private static final String CACHE_FILE_PATH = "target/healing-cache.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // SAVE CACHE TO JSON
    public static void saveCache(Map<String, HealingResult> cacheMap) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(CACHE_FILE_PATH), cacheMap);
            System.out.println("Persistent healing cache saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOAD CACHE FROM JSON
    public static Map<String, HealingResult> loadCache() {
        File cacheFile = new File(CACHE_FILE_PATH);
        if (!cacheFile.exists()) return new HashMap<>();
        try {
            return objectMapper.readValue(cacheFile, new TypeReference<Map<String, HealingResult>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}