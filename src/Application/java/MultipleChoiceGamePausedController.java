package Application.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MultipleChoiceGamePausedController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreLabel.setText("Highest score: " + HighScoreOfGame.getHighestScore1());
        FXMLManager fxmlManager = new FXMLManager();
        scoreLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.SEMI_BOLD, 24));
        scoreLabel.setStyle("-fx-text-fill: yellow;");
        gamePausedLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 48));
        mainMenuButton.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        resumeButton.fontProperty().bind(mainMenuButton.fontProperty());
        restartGameButton.fontProperty().bind(mainMenuButton.fontProperty());
    }

    public void backToGameMainMenu(ActionEvent event) {
        VBox parentVBox = (VBox) ((VBox) myGamePausedVBox.getParent()).getParent();
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        for (Node node : parentVBox.getChildren()) {
            node.setManaged(true);
            node.setVisible(true);
        }
    }

    public void restartGame(ActionEvent event) {
        VBox parentVBox = (VBox) ((VBox) myGamePausedVBox.getParent()).getParent();
        parentVBox.getChildren().remove(parentVBox.getChildren().size() - 1);
        FXMLManager fxmlManager = new FXMLManager();
        VBox root = (VBox) fxmlManager.getFXMLInsertedRoot("/FXML Files/MultipleChoiceGame.fxml");
        VBox.setVgrow(root, Priority.ALWAYS);
        parentVBox.getChildren().add(root);
    }

    public void resumeGame(ActionEvent event) {
        VBox middleVBox = (VBox) myGamePausedVBox.getParent();
        middleVBox.getChildren().remove(middleVBox.getChildren().size() - 1);
        for (Node node : middleVBox.getChildren()) {
            node.setManaged(true);
            node.setVisible(true);
        }
    }
}
