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
import javafx.util.Duration;

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
    private static int musicVolume = 50;
    private static int musicVolumeNotMuted = 50;
    private static boolean isMuted = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> highestScore2 = HighScoreOfGame.getHighestScore2(MemoryCardGameController.difficulty);

        String level = switch (MemoryCardGameController.difficulty) {
            case EASY -> "easy";
            case MEDIUM -> "medium";
            case HARD -> "hard";
        };
        scoreLabel.setText("Current highest score:");
        scoreLabel1.setText("Player: " + highestScore2.get(0) + " - " + level + " - Score: " + highestScore2.get(1) + "/10 - Time left: " + highestScore2.get(2) + "s");
        scoreLabel1.setStyle("-fx-text-fill: yellow;");
        FXMLManager fxmlManager = new FXMLManager();
        scoreLabel1.setFont(fxmlManager.cloneQuicksandFont(FontWeight.SEMI_BOLD, 18));
        scoreLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.SEMI_BOLD, 24));
        gamePausedLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 48));
        mainMenuButton.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        resumeButton.fontProperty().bind(mainMenuButton.fontProperty());
        restartGameButton.fontProperty().bind(mainMenuButton.fontProperty());

        volumeSlider.setValue(musicVolume);

        if ((int) volumeSlider.getValue() == 0) {
            isMuted = true;
            setImageIsMuted();
        } else if ((int) volumeSlider.getValue() != 0) {
            isMuted = false;
            setImageIsMuted();
        }

        volumeSlider.valueProperty().addListener((observableValue, number, t1) -> {
            MemoryCardGameController.mediaPlayer.setVolume((int) volumeSlider.getValue() / 100.0);
            if ((int) volumeSlider.getValue() == 0) {
                isMuted = true;
                setImageIsMuted();
            } else if ((int) volumeSlider.getValue() != 0) {
                isMuted = false;
                setImageIsMuted();
            }
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

        List<String> highestScore2Easy = HighScoreOfGame.getHighestScore2(MemoryCardGameController.MemoryGame.Difficulty.EASY);
        List<String> highestScore2Medium = HighScoreOfGame.getHighestScore2(MemoryCardGameController.MemoryGame.Difficulty.MEDIUM);
        List<String> highestScore2Hard = HighScoreOfGame.getHighestScore2(MemoryCardGameController.MemoryGame.Difficulty.HARD);

        Label infoGame3 = (Label) parentVBox.lookup("#infoGame3");
        if (infoGame3 != null) {
            infoGame3.setText(" Memory Game(Trò chơi lật thẻ bài)\n\n Highest score: \n"
                    + "-Player: " + highestScore2Easy.get(0) + " - Easy - Score: " + highestScore2Easy.get(1) + "/10 - Time left: " + highestScore2Easy.get(2) + "s."
                    + "\n-Player: " + highestScore2Medium.get(0) + " - Medium - Score: " + highestScore2Medium.get(1) + "/10 - Time left: " + highestScore2Medium.get(2) + "s."
                    + "\n-Player: " + highestScore2Hard.get(0) + " - Hard - Score: " + highestScore2Hard.get(1) + "/10 - Time left: " + highestScore2Hard.get(2) + "s."
                    + "\n\n Mô tả: Trò chơi lật các thẻ bài để tìm ra các cặp 3 thẻ bài\nkết hợp. "
                    + "Khi tìm được cặp thẻ bài sao cho trong đó có\n"
                    + "thẻ chứa từ tiếng Anh và 2 thẻ còn lại lần lượt chứa phiên\n"
                    + "âm và nghĩa tiếng Việt của từ đó, bạn sẽ được cộng 1 \n"
                    + "điểm. Trò chơi kết thúc khi bạn tìm được hết các cặp thẻ\nbài.");
        }
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
        musicVolume = (int) volumeSlider.getValue();
        if((int) volumeSlider.getValue() != 0) {
            musicVolumeNotMuted = (int) volumeSlider.getValue();
        }
        VBox parentVBox = (VBox) myGamePausedVBox.getParent();
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        parentVBox.getChildren().get(parentVBox.getChildren().size() - 1).setManaged(true);
        parentVBox.getChildren().get(parentVBox.getChildren().size() - 1).setVisible(true);
        MemoryCardGameController.isGamePaused = false;
    }

    public void mute(ActionEvent event) {
        try {
            if (MemoryCardGameController.mediaPlayer.isMute() && isMuted && musicVolume == 0) {
                Image image = new Image(new FileInputStream("src/Application/resources/AppIcon/volume.png"));
                myImageView.setImage(image);
                isMuted = false;
                MemoryCardGameController.mediaPlayer.setMute(false);
                volumeSlider.setValue(musicVolumeNotMuted);
            } else if (MemoryCardGameController.mediaPlayer.isMute()) {
                Image image = new Image(new FileInputStream("src/Application/resources/AppIcon/volume.png"));
                myImageView.setImage(image);
                isMuted = false;
                MemoryCardGameController.mediaPlayer.setMute(false);
                volumeSlider.setValue(musicVolumeNotMuted);
            } else {
                musicVolume = (int) volumeSlider.getValue();
                if((int) volumeSlider.getValue() != 0) {
                    musicVolumeNotMuted = (int) volumeSlider.getValue();
                }
                Image image = new Image(new FileInputStream("src/Application/resources/AppIcon/mute.png"));
                myImageView.setImage(image);
                volumeSlider.setValue(0);
                isMuted = true;
                MemoryCardGameController.mediaPlayer.setMute(true);
            }
        } catch (Exception e) {

        }
    }

    public void setImageIsMuted()  {
        try {
            if (isMuted) {
                Image image = new Image(new FileInputStream("src/Application/resources/AppIcon/mute.png"));
                myImageView.setImage(image);
            } else {
                Image image = new Image(new FileInputStream("src/Application/resources/AppIcon/volume.png"));
                myImageView.setImage(image);
            }
        } catch (Exception e) {

        }
    }
}