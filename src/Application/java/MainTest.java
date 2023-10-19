package Application.java;

import javafx.scene.media.MediaPlayer;

import javax.print.attribute.standard.Media;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.net.URI;
import java.net.http.*;
public class MainTest {

    public void start() {
        boolean isRunning = true;
        DictionaryManagement manager = new DictionaryManagement(Dictionary.getInstance());
        while (isRunning) {
            System.out.println("[1] Insert Word From File");
            System.out.println("[2] Insert New Word");
            System.out.println("[3] Show All English Words");
            System.out.println("[4] Show All Vietnamese Words");
            System.out.println("[5] Look Something Up?");
            System.out.println("[6] Exit");
            System.out.print("Type Your Option : ");
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextLine()) {
                case "1":
                    manager.insertWordFromFile();
                    break;
                case "2":
                    manager.insertWordFromCommandline();
                    break;
                case "3":
                    manager.showAllEnglishWords();
                    break;
                case "4":
                    manager.showAllVietnameseWords();
                    break;
                case "5":
                    System.out.println("Find what? : ");
                    System.out.println(manager.dictionaryLookup(scanner.nextLine(), true));
                    break;
                case "6":
                    isRunning = false;
                    break;
            }
        }
    }
    public static void main(String[] args) {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://text-to-speech-api3.p.rapidapi.com/speak?text=hello%20world!&lang=en"))
                    .header("X-RapidAPI-Key", "1d491d9bb6msh388f4ca7cddf6c4p17c694jsneaec5358edeb")
                    .header("X-RapidAPI-Host", "text-to-speech-api3.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            FileInputStream fileInputStream = new FileInputStream(response.body());;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
/*        MainTest program = new MainTest();
        program.start();*/
    }
}
