package Application.java;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.*;
import java.io.IOException;
public class SceneController {
    private boolean isEnglish;
    @FXML
    private Label resultLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private MenuButton languageMenuButton;
    public void translate(ActionEvent event) {
        try{
            DictionaryManagement dictionaryManagement = new DictionaryManagement(Dictionary.getInstance());
            resultLabel.setText(dictionaryManagement.dictionaryLookup(searchTextField.getText(), isEnglish));
        }
        catch (Exception e) {
            System.out.println("ồ, đã xảy ra lỗi : " + e);
        }
    }

    public void switchToEnglish(ActionEvent event) {
        isEnglish = true;
        languageMenuButton.setText("Tiếng Anh");
    }

    public void switchToVietnamese(ActionEvent event) {
        isEnglish = false;
        languageMenuButton.setText("Tiếng Việt");
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
