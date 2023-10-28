package Application.java;

import com.google.gson.Gson;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
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
    public void speak(String wordContent, String languageType) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://text-to-speech53.p.rapidapi.com/"))
                    .header("content-type", "application/json")
                    .header("X-RapidAPI-Key", "1d491d9bb6msh388f4ca7cddf6c4p17c694jsneaec5358edeb")
                    .header("X-RapidAPI-Host", "text-to-speech53.p.rapidapi.com")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\r\n    \"text\": \""
                            + wordContent
                            + "\",\r\n    \"lang\": \"" + languageType + "\",\r\n    \"format\": \"mp3\"\r\n}"))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            Speech speech = gson.fromJson(response.body(), Speech.class);
            Media media = new Media(speech.getSpeech());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (ConnectException e) {
            System.out.println("Ố ồ, đã mất kết nối internet!");
        }
        catch (InterruptedException e) {
            System.out.println("Trục trặc kết nối API!");
        }
        catch (IOException e) {
            System.out.println("Lỗi nhập xuất tệp!");
        }
    }
}
