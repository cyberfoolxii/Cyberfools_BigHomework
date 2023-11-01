package Application.java;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    private SplitPane splitPane1;
    @FXML
    private ListView<String> myListView;
    @FXML
            private Button tab0SpeakButton;
    @FXML
            private ScrollPane scrollPane1;
    @FXML
            private ScrollPane scrollPane2;
    @FXML
            private ScrollPane scrollPane3;
    @FXML
            private Tab tab4;
    @FXML
            private VBox vBox4;
    Stage currentStage;

    private EnglishWord currentEnglishWord;
    private VietnameseWord currentVietnameseWord;


    public void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát");
        alert.setHeaderText("Bạn sắp thoát ứng dụng!");
        alert.setContentText("Lưu trạng thái từ điển trước khi thoát?: ");
        if (alert.showAndWait().get() == ButtonType.OK) {
            currentStage = (Stage) splitPane1.getScene().getWindow();
            System.out.println("exit");
            LocalDictionaryManager.getInstance().exportWordToFile();
            currentStage.close();
        }
    }

    public void showAllWords() {
        LocalDictionaryManager.getInstance().showAllWords(vBox4);
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
        tab4.setOnSelectionChanged(event -> {
            showAllWords();
        });

        scrollPane1.setFitToWidth(true);
        scrollPane2.setFitToWidth(true);
        scrollPane3.setFitToWidth(true);
        scrollPane1.setFitToHeight(true);
        scrollPane2.setFitToHeight(true);
        scrollPane3.setFitToHeight(true);
    }

    public void define(ActionEvent event) {
        if (!tab1SearchTextField.getText().isEmpty()) OnlineDictionaryManager.getInstance().onlineDefinitionLookup(tab1SearchTextField.getText());
        LocalDictionaryManager.getInstance().showAllEnglishWords();
    }

    private void translate() {
        if (tab0searchTextField.getText().isEmpty()) return;
        myListView.setVisible(true);
        if (!LocalDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo, splitPane1)) {
            OnlineDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo);
            if(!LocalDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo, splitPane1)) {
                myListView.setVisible(false);
            }
        }
    }

    public void translate(ActionEvent event) {
        translate();
    }

    public void speak(ActionEvent event) {
        if (myListView.getItems().get(0) != null) {
            SpeechManager speechManager = new SpeechManager();
            speechManager.speak(myListView.getItems().get(0).split(" ")[0], translateFrom);
        }
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
