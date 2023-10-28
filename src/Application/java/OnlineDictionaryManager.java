package Application.java;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class OnlineDictionaryManager extends DictionaryManager {
    private Source[] sources;

    public Source[] getSources() {
        return sources;
    }
    public OnlineDictionaryManager(Dictionary dictionary) {
        super(dictionary);
    }

    private void onlineDictionaryLookup(String whatToLook, String from, String to) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://microsoft-translator-text.p.rapidapi.com/Dictionary/Lookup?to=" + to + "&api-version=3.0&from=" + from))
                    .header("content-type", "application/json")
                    .header("X-RapidAPI-Key", "1d491d9bb6msh388f4ca7cddf6c4p17c694jsneaec5358edeb")
                    .header("X-RapidAPI-Host", "microsoft-translator-text.p.rapidapi.com")
                    .method("POST", HttpRequest.BodyPublishers.ofString("[\r\n    {\r\n        \"Text\": \"" + whatToLook + "\"\r\n    }\r\n]"))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            sources = gson.fromJson(response.body(), Source[].class);
        } catch (Exception e) {
            System.out.println("error: onlineDictionaryLookup() in OnlineDictionaryManager class");
        }
    }

    @Override
    public String dictionaryLookup(String whatToLook, String from, String to) {
        onlineDictionaryLookup(whatToLook, from, to);
        StringBuilder result = new StringBuilder();
        for (Source source : sources) {
            Translation[] translations = source.getTranslations();
            for (Translation translation : translations) {
                result  .append("(")
                        .append(translation.getPosTag()).append(") ")
                        .append(translation.getPrefixWord()).append(" ")
                        .append(translation.getDisplayTarget()).append("\n");
            }
        }
        return result.toString();
    }
}
