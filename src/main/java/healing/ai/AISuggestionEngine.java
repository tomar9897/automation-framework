package healing.ai;
import healing.ai.GeminiClient;

public class AISuggestionEngine {

    public static String getSuggestedLocator(String failedLocator, String pageSource) {
        System.out.println("\nAI HEALING ACTIVATED");
        System.out.println("Failed Locator : " + failedLocator);
        
        String prompt = AIPromptBuilder.buildPrompt(failedLocator, pageSource);
        System.out.println("\n========= AI PROMPT =========\n");
        System.out.println(prompt);
        System.out.println("\n=============================\n");
        
        // FUTURE:
        // Gemini/OpenAI API integration will happen here
        // TEMP MOCK RESPONSE
        String aiResponse = GeminiClient.askGemini(prompt);
        System.out.println("\n========= GEMINI RESPONSE =========\n");
        System.out.println(aiResponse);
        System.out.println("\n===================================\n");
        return aiResponse;
       // return null;
    }
}