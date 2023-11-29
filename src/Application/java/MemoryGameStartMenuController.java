package Application.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;

public class MemoryGameStartMenuController implements Initializable {
    @FXML
    private VBox myVBox;
    @FXML
    private TextField playerNameField;
    @FXML
    private Button easyDifButton;
    @FXML
    private Button mediumDifButton;
    @FXML
    private Button hardDifButton;
    @FXML
    private Button backToMenuButton;
    @FXML
    private Label text1;
    @FXML
    private Label text2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLManager fxmlManager = new FXMLManager();
        text1.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        text2.fontProperty().bind(text1.fontProperty());
        playerNameField.fontProperty().bind(text1.fontProperty());

        backToMenuButton.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 20));
        easyDifButton.fontProperty().bind(backToMenuButton.fontProperty());
        mediumDifButton.fontProperty().bind(backToMenuButton.fontProperty());
        hardDifButton.fontProperty().bind(backToMenuButton.fontProperty());

        easyDifButton.setStyle("-fx-text-fill: LimeGreen;");
        mediumDifButton.setStyle("-fx-text-fill: yellow;");
        hardDifButton.setStyle("-fx-text-fill: red;");
    }

    public void startEasyGame(ActionEvent event) {
        startGame(MemoryCardGameController.MemoryGame.Difficulty.EASY);
    }

    public void startMediumGame(ActionEvent event) {
        startGame(MemoryCardGameController.MemoryGame.Difficulty.MEDIUM);
    }

    public void startHardGame(ActionEvent event) {
        startGame(MemoryCardGameController.MemoryGame.Difficulty.HARD);
    }

    public void backToMenu(ActionEvent event) {
        VBox vBox = (VBox) myVBox.getParent();
        vBox.getChildren().remove(vBox.getChildren().size() - 1);
        for (Node node : vBox.getChildren()) {
            node.setManaged(true);
            node.setVisible(true);
        }
    }

    private void startGame(MemoryCardGameController.MemoryGame.Difficulty difficulty) {
        String name = playerNameField.getText();
        VBox vBox = (VBox) myVBox.getParent();
        vBox.getChildren().remove(vBox.getChildren().size() - 1);
        MemoryCardGameController.difficulty = difficulty;
        FXMLManager fxmlManager = new FXMLManager();
        StackPane myStackPane = (StackPane) fxmlManager.getFXMLInsertedRoot("/FXML Files/MemoryCardGame.fxml");
        VBox.setVgrow(myStackPane, Priority.ALWAYS);
        vBox.getChildren().add(myStackPane);
        MemoryCardGameController.Player.playerName = name;
    }
}
