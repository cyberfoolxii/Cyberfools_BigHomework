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

import java.io.FileInputStream;
import java.util.Objects;

public final class FXMLManager {
    public static final String BOLD = "/Fonts/Quicksand/static/Quicksand-Bold.ttf";
    public static final String LIGHT = "/Fonts/Quicksand/static/Quicksand-Light.ttf";
    public static final String MEDIUM = "/Fonts/Quicksand/static/Quicksand-Medium.ttf";
    public static final String REGULAR = "/Fonts/Quicksand/static/Quicksand-Regular.ttf";
    public static final String SEMI_BOLD = "/Fonts/Quicksand/static/Quicksand-SemiBold.ttf";

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
    public Label cloneLabel(String content, Pos pos, FontWeight fontWeight, double size) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML Files/CloneSearchLabel.fxml"));
        Label l = null;
        try {
            Font font = cloneQuicksandFont(fontWeight, size);
            l = fxmlLoader.load();
            l.setText(content);
            l.setAlignment(pos);
            l.setFont(font);
        } catch (Exception e) {
            System.out.println("lỗi nhân bản Label");
        }
        return l;
    }

    public Font cloneQuicksandFont(FontWeight fontWeight, double size) {
        Font font = null;
        switch (fontWeight) {
            case BOLD:
                font = Font.loadFont(getClass().getResourceAsStream(FXMLManager.BOLD), size);
                break;
            case THIN:
                font = Font.loadFont(getClass().getResourceAsStream(FXMLManager.LIGHT), size);
                break;
            case NORMAL:
                font = Font.loadFont(getClass().getResourceAsStream(FXMLManager.REGULAR), size);
                break;
            case SEMI_BOLD:
                font = Font.loadFont(getClass().getResourceAsStream(FXMLManager.SEMI_BOLD), size);
                break;
            case MEDIUM:
                font = Font.loadFont(getClass().getResourceAsStream(FXMLManager.MEDIUM), size);
        }
        return font;
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
            textArea.setFont(cloneQuicksandFont(FontWeight.BOLD, 18));
        } catch (Exception e) {
            System.out.println("lỗi nhân bản TextField");
        }
        return textArea;
    }
}
