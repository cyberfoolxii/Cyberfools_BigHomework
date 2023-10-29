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

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {
    private final Dictionary dictionary = new Dictionary();
    OnlineDictionaryManager onlineDictionaryManager = new OnlineDictionaryManager(dictionary);
    LocalDictionaryManager localDictionaryManager = new LocalDictionaryManager(dictionary);

    public SceneController() {
        localDictionaryManager.insertWordFromFile();
    }

    private String translateFrom = "en";
    private String translateTo = "vi";
    @FXML
    private Label resultLable;
    @FXML
    private Label searchWordContent;
    @FXML
    private TextField searchTextField;
    @FXML
    private MenuButton languageMenuButton;
    @FXML
    private Button speakButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventHandler<KeyEvent> keyEventEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    translate();
                }
            }
        };
        searchTextField.setOnKeyPressed(keyEventEventHandler);
    }

    private void found(String result) {
        resultLable.setText(result);
        speakButton.setVisible(true);
        searchWordContent.setText(searchTextField.getText());
    }

    private void reset() {
        speakButton.setVisible(false);
        searchWordContent.setText("");
        resultLable.setText("");
    }

    private void translate() {
        localDictionaryManager.showAllEnglishWords();
        System.out.println();
        localDictionaryManager.showAllVietnameseWords();
        System.out.println("----------------------------------------");
        reset();
        String result = localDictionaryManager.dictionaryLookup(searchTextField.getText(), translateFrom, translateTo);
        if (result.isEmpty()) {
            result = onlineDictionaryManager.dictionaryLookup(searchTextField.getText(), translateFrom, translateTo);
            if (!result.isEmpty()) {
                found(result);
                localDictionaryManager.insertWordFromAPI(onlineDictionaryManager.getSources(), translateFrom);
                return;
            }
            searchWordContent.setText("Hmm, từ này có vẻ hơi lạ, thêm từ mới?");
            return;
        }
        found(result);
        //localDictionaryManager.showAllEnglishWords();
        //System.out.println("--------------------------------");
        //localDictionaryManager.showAllVietnameseWords();
    }

    public void translate(ActionEvent event) {
        translate();
    }

    public void speak(ActionEvent event) {
        if (!searchWordContent.getText().isEmpty()) {
            SpeechManager speechManager = new SpeechManager();
            speechManager.speak(searchWordContent.getText(), translateFrom);
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
