package Application.java;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public final class FXMLManager {
    public Parent getFXMLInsertedRoot(String fxmlFilePath) {
        try {
            return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFilePath)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

/*    public SceneController getController(String fxmlFilePath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
        return  fxmlLoader.getController();
    }*/
    public Label cloneLabel(String content, Pos pos, FontPosture fontPosture, FontWeight fontWeight) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML Files/CloneSearchLabel.fxml"));
        Label l = null;
        try {
            l = fxmlLoader.load();
            l.setText(content);
            l.setAlignment(pos);
            l.setFont(Font.font("Corbel", fontWeight, fontPosture, 25.0));
        } catch (Exception e) {
            System.out.println("lỗi nhân bản Label");
        }
        return l;
    }

    public Button cloneSpeakerButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML Files/CloneSpeaker.fxml"));
        Button button = null;
        try {
            button = fxmlLoader.load();
        } catch (Exception e) {
            System.out.println("lỗi nhân bản Button");
        }
        return button;
    }

    public TextArea cloneTextArea(String text, String promptText) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML Files/CloneTextArea.fxml"));
        TextArea textArea = null;
        try {
            textArea = fxmlLoader.load();
            if (text != null) {
                textArea.setText(text);
            }
            if (promptText != null) {
                textArea.setPromptText(promptText);
            }
        } catch (Exception e) {
            System.out.println("lỗi nhân bản TextField");
        }
        return textArea;
    }
}
