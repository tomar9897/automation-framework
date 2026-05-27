package healing.ai;

public class AIPromptBuilder {

    public static String buildPrompt(String failedLocator, String domSnapshot) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an AI locator healing engine.\n\n");
        prompt.append("A Selenium locator failed.\n");
        prompt.append("Failed Locator:\n");
        prompt.append(failedLocator).append("\n\n");
        prompt.append("Available DOM Elements:\n");
        prompt.append(domSnapshot).append("\n\n");
        prompt.append("Your task:\n");
        prompt.append("- Identify the most likely matching element.\n");
        prompt.append("- Return ONLY a valid XPath locator.\n");
        prompt.append("- Do not explain anything.\n");
        return prompt.toString();
    }
}