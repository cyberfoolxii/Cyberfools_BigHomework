package Application.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.FontWeight;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;

import static Application.java.MemoryCardGameController.Card.numberOfFlippedCards;

public class MemoryGamePausedController implements Initializable {
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button restartGameButton;
    @FXML
    private Button resumeButton;
    @FXML
    private Label gamePausedLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label scoreLabel1;
    @FXML
    private VBox myGamePausedVBox;
    @FXML
    private Button speakerButton;
    @FXML
    private ImageView myImageView;
    @FXML
    private Slider volumeSlider;

    private int musicVolume = 50;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> highestScore2 = HighScoreOfGame.getHighestScore2(MemoryCardGameController.difficulty);

        String level = switch (MemoryCardGameController.difficulty) {
            case EASY -> "easy";
            case MEDIUM -> "medium";
            case HARD -> "hard";
        };
        scoreLabel.setText("Highest score of " + level + " level:");
        scoreLabel1.setText("Player: " + highestScore2.get(0) + " - Score: " + highestScore2.get(1) + "/10 - Time left: " + highestScore2.get(2) + "s");
        scoreLabel1.setStyle("-fx-text-fill: yellow;");
        FXMLManager fxmlManager = new FXMLManager();
        scoreLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.SEMI_BOLD, 24));
        gamePausedLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 48));
        mainMenuButton.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        resumeButton.fontProperty().bind(mainMenuButton.fontProperty());
        restartGameButton.fontProperty().bind(mainMenuButton.fontProperty());

        volumeSlider.valueProperty().addListener((observableValue, number, t1) -> {
            MemoryCardGameController.mediaPlayer.setVolume((int) volumeSlider.getValue() / 100.0);
        });
    }

    public void backToGameMainMenu(ActionEvent event) {
        numberOfFlippedCards = 0;
        MemoryCardGameController.isGameRestarted = true;
        MemoryCardGameController.isGamePaused = false;
        VBox parentVBox = (VBox) myGamePausedVBox.getParent();
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        for (Node node : parentVBox.getChildren()) {
            node.setManaged(true);
            node.setVisible(true);
        }

        MemoryCardGameController.mediaPlayer.stop();
    }

    public void restartGame(ActionEvent event) {
        numberOfFlippedCards = 0;
        MemoryCardGameController.isGameRestarted = true;
        MemoryCardGameController.isGamePaused = false;
        MemoryCardGameController.mediaPlayer.stop();
        VBox parentVBox = (VBox) myGamePausedVBox.getParent();
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        FXMLManager fxmlManager = new FXMLManager();
        StackPane myStackPane = (StackPane) fxmlManager.getFXMLInsertedRoot("/FXML Files/MemoryCardGame.fxml");
        VBox.setVgrow(myStackPane, Priority.ALWAYS);
        parentVBox.getChildren().add(myStackPane);
    }

    public void resumeGame(ActionEvent event) {
        VBox parentVBox = (VBox) myGamePausedVBox.getParent();
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        parentVBox.getChildren().get(parentVBox.getChildren().size() - 1).setManaged(true);
        parentVBox.getChildren().get(parentVBox.getChildren().size() - 1).setVisible(true);
        MemoryCardGameController.isGamePaused = false;
    }

    public void mute(ActionEvent event) {
        try {
            if (MemoryCardGameController.mediaPlayer.isMute()) {
                Image image = new Image(new FileInputStream("src/Application/resources/AppIcon/volume.png"));
                myImageView.setImage(image);
                MemoryCardGameController.mediaPlayer.setMute(false);
                volumeSlider.setValue(musicVolume);
            } else {
                musicVolume = (int) volumeSlider.getValue();
                Image image = new Image(new FileInputStream("src/Application/resources/AppIcon/mute.png"));
                myImageView.setImage(image);
                volumeSlider.setValue(0);
                MemoryCardGameController.mediaPlayer.setMute(true);
            }
        } catch (Exception e) {

        }
    }
}
