package Application.java;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Speech {
    private MediaPlayer sound;

    public Speech() {
        File f = new File("src\\Application\\resources\\Audio\\audio.mp3");
        Media media = new Media(f.toURI().toString());
        sound = new MediaPlayer(media);
    }

    public void play(String textToSpeech) {
        try{
            String apiPath = "https://text-to-speech-api3.p.rapidapi.com/speak?text=" +
                    convertFormat(textToSpeech) + "&lang=en";
            File f = new File("src\\Application\\resources\\Audio\\audio.mp3");
            URI u = f.toURI();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiPath))
                    .header("X-RapidAPI-Key", "1d491d9bb6msh388f4ca7cddf6c4p17c694jsneaec5358edeb")
                    .header("X-RapidAPI-Host", "text-to-speech-api3.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            client.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get(u)));
            Media media = new Media(u.toString());
            MediaPlayer player = new MediaPlayer(media);
            player.play();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String convertFormat(String wordContent) {
        StringBuilder result = new StringBuilder(wordContent);
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == ' ') {
                result.replace(i, i + 1, "%20");
            }
        }
        return result.toString();
    }
}
