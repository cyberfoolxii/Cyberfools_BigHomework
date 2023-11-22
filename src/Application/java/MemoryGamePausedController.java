package Application.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.FontWeight;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

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
    private VBox myGamePausedVBox;
    @FXML
    private Button speakerButton;
    @FXML
    private ImageView myImageView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreLabel.setText("Highest score: " + HighScoreOfGame.getHighestScore1());
        FXMLManager fxmlManager = new FXMLManager();
        scoreLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.SEMI_BOLD, 18));
        gamePausedLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 30));
        mainMenuButton.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        resumeButton.fontProperty().bind(mainMenuButton.fontProperty());
        restartGameButton.fontProperty().bind(mainMenuButton.fontProperty());

    }

    public void backToGameMainMenu(ActionEvent event) {
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
        VBox parentVBox = (VBox) myGamePausedVBox.getParent();
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        FXMLManager fxmlManager = new FXMLManager();
        BorderPane myBorderPane = (BorderPane) fxmlManager.getFXMLInsertedRoot("/FXML Files/MemoryCardGame.fxml");
        VBox.setVgrow(myBorderPane, Priority.ALWAYS);
        parentVBox.getChildren().add(myBorderPane);

        MemoryCardGameController.mediaPlayer.stop();
        MemoryCardGameController.mediaPlayer.play();
    }

    public void resumeGame(ActionEvent event) {
        VBox parentVBox = (VBox) myGamePausedVBox.getParent();
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        parentVBox.getChildren().get(parentVBox.getChildren().size() - 1).setManaged(true);
        parentVBox.getChildren().get(parentVBox.getChildren().size() - 1).setVisible(true);
    }

    public void mute(ActionEvent event) {
        try {
            if (MemoryCardGameController.mediaPlayer.isMute()) {
                Image image = new Image(new FileInputStream("src/Application/resources/AppIcon/volume.png"));
                myImageView.setImage(image);
                MemoryCardGameController.mediaPlayer.setMute(false);
            } else {
                Image image = new Image(new FileInputStream("src/Application/resources/AppIcon/mute.png"));
                myImageView.setImage(image);
                MemoryCardGameController.mediaPlayer.setMute(true);
            }
        } catch (Exception e) {

        }
    }
}
