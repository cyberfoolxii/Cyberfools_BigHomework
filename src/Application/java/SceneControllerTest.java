package Application.java;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.*;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneControllerTest {
    private String translateFrom;
    private String translateTo;
    @FXML
    private TextField resultTextField;
    @FXML
    private TextField searchTextField;
    @FXML
    private MenuButton languageMenuButton;
    @FXML
    private Button speakButton;

    public void translate(ActionEvent event) {
/*        DictionaryManagement dictionaryManagement = new DictionaryManagement(Dictionary.getInstance());
        resultTextField.setText(dictionaryManagement.dictionaryLookup(searchTextField.getText(), languageType));*/
        Dictionary dictionary = new Dictionary();
        OnlineDictionaryManager onlineDictionaryManager = new OnlineDictionaryManager(dictionary);
        LocalDictionaryManager localDictionaryManager = new LocalDictionaryManager(dictionary);
        String result = onlineDictionaryManager.dictionaryLookup(searchTextField.getText(), translateFrom, translateTo);
        resultTextField.setText(result);
        localDictionaryManager.insertWordFromAPI(onlineDictionaryManager.getSources(), translateFrom);
        localDictionaryManager.showAllEnglishWords();
        System.out.println("--------------------------------");
        localDictionaryManager.showAllVietnameseWords();
    }

    public void speak(ActionEvent event) {
        if (!searchTextField.getText().isEmpty()) {
            SpeechManager speechManager = new SpeechManager();
            speechManager.speak(searchTextField.getText(), translateFrom);
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

    /** sửa sau. */
    public void switchLanguageToDefault(ActionEvent event) {
        translateFrom = "";
        languageMenuButton.setText("Tự động");
    }

    public void switchScene(ActionEvent event) throws IOException {
        RootManager rootManager = new RootManager();
        Parent root = rootManager.getFXMLInsertedRoot("DictionaryApplication.fxml");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
