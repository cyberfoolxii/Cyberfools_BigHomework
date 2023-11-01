package Application.java;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.Objects;

public final class FXMLManager {
    public Parent getFXMLInsertedRoot(String fxmlFilePath) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFilePath)));
    }

/*    public SceneController getController(String fxmlFilePath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
        return  fxmlLoader.getController();
    }*/
    public Label cloneLabel(String content, Pos pos, FontPosture fontPosture, FontWeight fontWeight) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML Files/CloneSearchedLabel.fxml"));
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
}
