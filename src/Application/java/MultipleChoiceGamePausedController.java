package Application.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

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
