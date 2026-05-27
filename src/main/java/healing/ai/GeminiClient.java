package healing.ai;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GeminiClient {

    private static final String API_KEY;

    static {
        Properties properties = new Properties();
        try (InputStream input = GeminiClient.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        API_KEY = properties.getProperty("gemini.api.key");
    }

    public static String askGemini(String prompt) {
        try {
            OkHttpClient client = new OkHttpClient();
            JSONObject textPart = new JSONObject();
            textPart.put("text", prompt);
            JSONArray parts = new JSONArray();
            parts.put(textPart);
            JSONObject content = new JSONObject();
            content.put("parts", parts);
            JSONArray contents = new JSONArray();
            contents.put(content);
            JSONObject requestBodyJson = new JSONObject();
            
            
            requestBodyJson.put("contents", contents);
            RequestBody body = RequestBody.create(requestBodyJson.toString(), MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=" + API_KEY)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            
         // Check HTTP Status code first before parsing JSON
            if (!response.isSuccessful()) {
                System.out.println("HTTP Error Code: " + response.code());
                System.out.println("Response: " + responseBody);
                return null;
            }
            
//            System.out.println("\n========= RAW GEMINI RESPONSE =========\n" );
//            System.out.println(responseBody);
            System.out.println( "\n=======================================\n");
            JSONObject json = new JSONObject(responseBody);
            if (json.has("error")) {
                System.out.println("\nGemini API Error Detected\n");
                System.out.println(json.getJSONObject("error").getString("message"));
                return null;
            }
            return json.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}