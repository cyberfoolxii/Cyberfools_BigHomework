package Application.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;


public class UpdateWordController implements Initializable {
    @FXML
    private ScrollPane myScrollPane;
    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;
    @FXML
    private VBox vBox3;
    @FXML
    private VBox vBox4;
    @FXML
    private TextArea wordContent;
    @FXML
    private TextArea wordType;
    @FXML
    private TextArea wordMeaning;
    @FXML
    private TextArea wordDefinition;
    @FXML
    private TextArea wordSynonym;
    @FXML
    private TextArea wordAntonym;
    @FXML
    private TextArea wordPhonetic;
    @FXML
    private Label messageLabel;
    private static VBox crossVBox;

    public static VBox getCrossVBox() {
        return crossVBox;
    }

    public static void setCrossVBox(VBox crossVBox) {
        UpdateWordController.crossVBox = crossVBox;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myScrollPane.setFitToWidth(true);
    }

    public void create(EnglishWord englishWord) {
        if (englishWord != null) {
            FXMLManager fxmlManager = new FXMLManager();
            wordContent.setText(englishWord.getWordContent());
            wordType.setText(englishWord.getWordType());

            for (VietnameseWord meaning : englishWord.getVietnameseMeaningsList()) {
                TextArea textArea = fxmlManager.cloneTextArea(meaning.getWordContent(), wordMeaning.getPromptText());
                vBox1.getChildren().add(textArea);
            }
            for (String def : englishWord.getDefinitions()) {
                TextArea textArea = fxmlManager.cloneTextArea(def, wordDefinition.getPromptText());
                vBox2.getChildren().add(textArea);
            }
            for (String syn : englishWord.getSynonyms()) {
                TextArea textArea = fxmlManager.cloneTextArea(syn, wordSynonym.getPromptText());
                vBox3.getChildren().add(textArea);
            }
            for (String ant : englishWord.getAntonyms()) {
                TextArea textArea = fxmlManager.cloneTextArea(ant, wordAntonym.getPromptText());
                vBox4.getChildren().add(textArea);
            }
        }
    }

    public void addMeaning(ActionEvent event) {
        FXMLManager fxmlManager = new FXMLManager();
        TextArea textArea = fxmlManager.cloneTextArea(null, wordMeaning.getPromptText());
        vBox1.getChildren().add(textArea);
    }

    public void reduceMeaning(ActionEvent event) {
        if (vBox1.getChildren().size() > 1) {
            vBox1.getChildren().remove(vBox1.getChildren().size() - 1);
        }
    }

    public void addDefinition(ActionEvent event) {
        FXMLManager fxmlManager = new FXMLManager();
        TextArea textArea = fxmlManager.cloneTextArea(null, wordDefinition.getPromptText());
        vBox2.getChildren().add(textArea);
    }

    public void reduceDefinition(ActionEvent event) {
        if (vBox2.getChildren().size() > 1) {
            vBox2.getChildren().remove(vBox2.getChildren().size() - 1);
        }
    }

    public void addSynonym(ActionEvent event) {
        FXMLManager fxmlManager = new FXMLManager();
        TextArea textArea = fxmlManager.cloneTextArea(null, wordSynonym.getPromptText());
        vBox3.getChildren().add(textArea);
    }

    public void reduceSynonym(ActionEvent event) {
        if (vBox3.getChildren().size() > 1) {
            vBox3.getChildren().remove(vBox3.getChildren().size() - 1);
        }
    }

    public void addAntonym(ActionEvent event) {
        FXMLManager fxmlManager = new FXMLManager();
        TextArea textArea = fxmlManager.cloneTextArea(null, wordAntonym.getPromptText());
        vBox4.getChildren().add(textArea);
    }

    public void reduceAntonym(ActionEvent event) {
        if (vBox4.getChildren().size() > 1) {
            vBox4.getChildren().remove(vBox4.getChildren().size() - 1);
        }
    }

    public void complete(ActionEvent event) {
        if (wordContent.getText().isEmpty()
                || wordType.getText().isEmpty()) {
            messageLabel.setStyle("-fx-text-fill: #FF0000;");
            messageLabel.setText("Nhập thiếu thông tin!");
            return;
        }
        messageLabel.setStyle("-fx-text-fill: #1ad963;");
        messageLabel.setText("Hoàn thành!");

        EnglishWord englishWord = new EnglishWord(wordContent.getText(), wordType.getText());
        if (!wordPhonetic.getText().isEmpty()) {
            englishWord.setPhonetic(wordPhonetic.getText());
        }

        for (int i = 0; i < vBox1.getChildren().size(); i++) {
            String content = ((TextArea) vBox1
                    .getChildren()
                    .get(i))
                    .getText();
            if (content.isEmpty()) {
                continue;
            }
            VietnameseWord vietnameseWord = new VietnameseWord(content, wordType.getText());
            vietnameseWord.addToEnglishMeaningsList(englishWord);
            englishWord.addToVietnameseMeaningsList(vietnameseWord);
            LocalDictionaryManager.getInstance().insertWordToDictionary(vietnameseWord);
        }

        for (int i = 0; i < vBox2.getChildren().size(); i++) {
            String def = ((TextArea) vBox2.getChildren().get(i)).getText();
            if (def.isEmpty()) {
                continue;
            }
            englishWord.getDefinitions().add(def);
        }
        for (int i = 0; i < vBox3.getChildren().size(); i++) {
            String syn = ((TextArea) vBox3.getChildren().get(i)).getText();
            if (syn.isEmpty()) {
                continue;
            }
            englishWord.getSynonyms().add(syn);
        }
        for (int i = 0; i < vBox4.getChildren().size(); i++) {
            String ant = ((TextArea) vBox4.getChildren().get(i)).getText();
            if (ant.isEmpty()) {
                continue;
            }
            englishWord.getAntonyms().add(ant);
        }
        LocalDictionaryManager.getInstance().insertWordToDictionary(englishWord);
        myScrollPane.setContent(messageLabel);
        reset();
    }

    public static void reset() {
        if (crossVBox == null) {
            return;
        }
        crossVBox.getChildren().remove(crossVBox.getChildren().size() - 1);
        for (Node node : crossVBox.getChildren()) {
            node.setManaged(true);
        }
    }
}
