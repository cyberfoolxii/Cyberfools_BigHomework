package Application.java;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.event.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {

    private String translateFrom = "en";
    private String translateTo = "vi";
    @FXML
    private TextField tab0searchTextField;
    @FXML
    private TextField tab1SearchTextField;
    @FXML
    private MenuButton languageMenuButton;
    @FXML
    private SplitPane tab0SplitPane;
    Stage currentStage;

    private EnglishWord currentEnglishWord;
    private VietnameseWord currentVietnameseWord;


    public void Exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát");
        alert.setHeaderText("Bạn sắp thoát ứng dụng!");
        alert.setContentText("Lưu trạng thái từ điển trước khi thoát?: ");
        if (alert.showAndWait().get() == ButtonType.OK) {
            currentStage = (Stage) tab0SplitPane.getScene().getWindow();
            System.out.println("exit");
            LocalDictionaryManager.getInstance().exportWordToFile();
            currentStage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
/*        EventHandler<KeyEvent> keyEventEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    translate();
                }
            }
        };
        searchTextField1.setOnKeyPressed(keyEventEventHandler);*/
    }

    public void define(ActionEvent event) {
        if (!tab1SearchTextField.getText().isEmpty()) OnlineDictionaryManager.getInstance().onlineDefinitionLookup(tab1SearchTextField.getText());
        LocalDictionaryManager.getInstance().showAllEnglishWords();
    }

    private void translate() {
        if (!LocalDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, tab0SplitPane)) {
            OnlineDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo);
            LocalDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, tab0SplitPane);
        }
    }

    public void translate(ActionEvent event) {
        translate();
    }

    public void speak(ActionEvent event) {
 /*       if (!searchWordContent.getText().isEmpty()) {
            SpeechManager speechManager = new SpeechManager();
            speechManager.speak(searchWordContent.getText(), translateFrom);
        }*/
    }

    /** sửa sau. */
    public void switchLanguageToEnglish(ActionEvent event) {
        translateFrom = "en";
        translateTo = "vi";
        languageMenuButton.setText("Tiếng Anh");
    }

    /** sửa sau. */
    public void switchLanguageToVietnamese(ActionEvent event) {
        translateFrom = "vi";
        translateTo = "en";
        languageMenuButton.setText("Tiếng Việt");
    }

    public void switchScene(ActionEvent event) throws IOException {
        FXMLManager rootManager = new FXMLManager();
        Parent root = rootManager.getFXMLInsertedRoot("DictionaryApplication.fxml");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
