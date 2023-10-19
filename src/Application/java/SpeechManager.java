package Application.java;

import com.google.gson.Gson;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpeechManager {
    private static class Speech {
        private String speech;

        public String getSpeech() {
            return speech;
        }

    }
    public void speak(String wordContent, boolean isEnglish) {
        String region;
        if (isEnglish) {
            region = "en";
        } else {
            region = "vi";
        }
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://text-to-speech53.p.rapidapi.com/"))
                    .header("content-type", "application/json")
                    .header("X-RapidAPI-Key", "1d491d9bb6msh388f4ca7cddf6c4p17c694jsneaec5358edeb")
                    .header("X-RapidAPI-Host", "text-to-speech53.p.rapidapi.com")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\r\n    \"text\": \""
                            + wordContent
                            +"\",\r\n    \"lang\": \""+ region +"\",\r\n    \"format\": \"mp3\"\r\n}"))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            Speech speech = gson.fromJson(response.body(), Speech.class);
            Media media = new Media(speech.getSpeech());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
        catch (Exception e) {
            System.out.println("không đọc được từ!" + e);
        }
    }
}
