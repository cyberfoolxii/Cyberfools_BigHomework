package Application.java;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameApp extends Application {

    private List<String> words = Arrays.asList(
            "I used to be a baker because I kneaded dough.",
            "I told my wife she was drawing her eyebrows too high. She looked surprised.",
            "I used to play piano by ear, but now I use my hands.",
            "I'm reading a book on anti-gravity. It's impossible to put down.",
            "I used to be a baker because I kneaded dough."
    );

    private List<String> puns = Arrays.asList("Baker", "Surprised", "Pianist", "Anti-gravity", "Kneaded");

    private int currentIndex = 0;
    private int score = 0;

    public static void main(String[] args) {
        launch(args);
    }

    Stage primaryStage = null;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Enhanced English Pun Game");

        // Load Quicksand font
        Font.loadFont(getClass().getResourceAsStream("/Fonts.Quicksand/static/Quicksand-Regular.ttf"), 14);

        GridPane gridPane = createGridPane();
        addWords(gridPane);

        Scene scene = new Scene(gridPane, 600, 400);
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        this.primaryStage = primaryStage;
        primaryStage.show();
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        return gridPane;
    }

    private void addWords(GridPane gridPane) {
        Label wordLabel = new Label("Word: ");
        Label currentWord = new Label(words.get(currentIndex));
        Button nextButton = new Button("Next");
        Label scoreLabel = new Label("Score: " + score);

        HBox buttonsBox = createPunButtons();

        nextButton.setOnAction(event -> {
            currentIndex = (currentIndex + 1) % words.size();
            currentWord.setText(words.get(currentIndex));
            buttonsBox.getChildren().clear();
            buttonsBox.getChildren().addAll(createPunButtons().getChildren());
        });

        gridPane.add(wordLabel, 0, 0);
        gridPane.add(currentWord, 1, 0);
        gridPane.add(createPunButtons(), 1, 1);
        gridPane.add(nextButton, 1, 2);
        gridPane.add(scoreLabel, 1, 3);
    }

    private HBox createPunButtons() {
        HBox punButtons = new HBox(10);
        punButtons.setAlignment(Pos.CENTER);

        Collections.shuffle(puns); // Shuffle puns for random order

        for (String pun : puns) {
            Button punButton = new Button(pun);
            punButton.setOnAction(event -> handlePunButtonClick(pun));
            punButtons.getChildren().add(punButton);
        }

        return punButtons;
    }

    private void handlePunButtonClick(String selectedPun) {
        if (selectedPun.equals(puns.get(0))) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong! Try again.");
        }
        // Update the score label
        ((Label) ((VBox) ((GridPane) primaryStage.getScene().getRoot()).getChildren().get(4)).getChildren().get(2)).setText("Score: " + score);
    }
}
