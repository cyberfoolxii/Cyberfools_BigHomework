package Application.java;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    @FXML
    private Button add1;
    @FXML
    private Button add2;
    @FXML
    private Button add3;
    @FXML
    private Button add4;
    @FXML
    private Button reduce1;
    @FXML
    private Button reduce2;
    @FXML
    private Button reduce3;
    @FXML
    private Button reduce4;
    @FXML
    private Button completeButton;
    private static VBox crossVBox;
    private static Word currentWord;
    public static VBox getCrossVBox() {
        return crossVBox;
    }

    public static void setCrossVBox(VBox crossVBox) {
        UpdateWordController.crossVBox = crossVBox;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add1.prefHeightProperty().bind(wordMeaning.heightProperty());
        add2.prefHeightProperty().bind(wordDefinition.heightProperty());
        add3.prefHeightProperty().bind(wordSynonym.heightProperty());
        add4.prefHeightProperty().bind(wordAntonym.heightProperty());
        reduce1.prefHeightProperty().bind(wordMeaning.heightProperty());
        reduce2.prefHeightProperty().bind(wordDefinition.heightProperty());
        reduce3.prefHeightProperty().bind(wordSynonym.heightProperty());
        reduce4.prefHeightProperty().bind(wordAntonym.heightProperty());
        FXMLManager fxmlManager = new FXMLManager();
        add1.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 16));
        add2.fontProperty().bind(add1.fontProperty());
        add3.fontProperty().bind(add1.fontProperty());
        add4.fontProperty().bind(add1.fontProperty());
        completeButton.fontProperty().bind(add1.fontProperty());
        reduce1.fontProperty().bind(add1.fontProperty());
        reduce2.fontProperty().bind(add1.fontProperty());
        reduce3.fontProperty().bind(add1.fontProperty());
        reduce4.fontProperty().bind(add1.fontProperty());
        wordMeaning.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        wordType.fontProperty().bind(wordMeaning.fontProperty());
        wordContent.fontProperty().bind(wordMeaning.fontProperty());
        wordAntonym.fontProperty().bind(wordMeaning.fontProperty());
        wordSynonym.fontProperty().bind(wordMeaning.fontProperty());
        wordPhonetic.fontProperty().bind(wordMeaning.fontProperty());
        wordDefinition.fontProperty().bind(wordMeaning.fontProperty());
        messageLabel.fontProperty().bind(add1.fontProperty());
    }

    public void create(Word word) {
        currentWord = word;
        FXMLManager fxmlManager = new FXMLManager();
        if (word instanceof EnglishWord) {
            EnglishWord englishWord = (EnglishWord) word;
            wordContent.setText(englishWord.getWordContent());
            wordType.setText(englishWord.getWordType());

            for (VietnameseWord meaning : englishWord.getVietnameseMeaningsList()) {
                if (wordMeaning.getText().isEmpty()) {
                    wordMeaning.setText(meaning.getWordContent());
                } else {
                    TextArea textArea = fxmlManager.cloneTextArea(meaning.getWordContent(), wordMeaning.getPromptText());
                    VBox.setVgrow(textArea, Priority.ALWAYS);
                    vBox1.getChildren().add(textArea);
                }
            }
            for (String def : englishWord.getDefinitions()) {
                if (wordDefinition.getText().isEmpty()) {
                    wordDefinition.setText(def);
                } else {
                    TextArea textArea = fxmlManager.cloneTextArea(def, wordDefinition.getPromptText());
                    VBox.setVgrow(textArea, Priority.ALWAYS);
                    vBox2.getChildren().add(textArea);
                }
            }
            for (String syn : englishWord.getSynonyms()) {
                if (wordSynonym.getText().isEmpty()) {
                    wordSynonym.setText(syn);
                } else {
                    TextArea textArea = fxmlManager.cloneTextArea(syn, wordSynonym.getPromptText());
                    VBox.setVgrow(textArea, Priority.ALWAYS);
                    vBox3.getChildren().add(textArea);
                }
            }
            for (String ant : englishWord.getAntonyms()) {
                if (wordAntonym.getText().isEmpty()) {
                    wordAntonym.setText(ant);
                } else {
                    TextArea textArea = fxmlManager.cloneTextArea(ant, wordAntonym.getPromptText());
                    VBox.setVgrow(textArea, Priority.ALWAYS);
                    vBox4.getChildren().add(textArea);
                }
            }
        } else if (word instanceof VietnameseWord) {
            VietnameseWord vietnameseWord = (VietnameseWord) word;
            wordPhonetic.setVisible(false);
            wordPhonetic.setManaged(false);
            VBox vBox = (VBox) myScrollPane.getContent();
            for (int i = 4; i < 7; i++) {
                vBox.getChildren().get(i).setVisible(false);
                vBox.getChildren().get(i).setManaged(false);
            }
            wordContent.setText(vietnameseWord.getWordContent());
            wordType.setText(vietnameseWord.getWordType());
            for (EnglishWord englishWordMeaning : vietnameseWord.getEnglishMeaningsList()) {
                if (wordMeaning.getText().isEmpty()) {
                    wordMeaning.setText(englishWordMeaning.getWordContent());
                } else {
                    TextArea textArea = fxmlManager.cloneTextArea(englishWordMeaning.getWordContent(), wordAntonym.getPromptText());
                    VBox.setVgrow(textArea, Priority.ALWAYS);
                    vBox1.getChildren().add(textArea);
                }
            }
        }
    }

    public void addMeaning(ActionEvent event) {
        FXMLManager fxmlManager = new FXMLManager();
        TextArea textArea = fxmlManager.cloneTextArea(null, wordMeaning.getPromptText());
        VBox.setVgrow(textArea, Priority.ALWAYS);
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
        VBox.setVgrow(textArea, Priority.ALWAYS);
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
        VBox.setVgrow(textArea, Priority.ALWAYS);
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
        VBox.setVgrow(textArea, Priority.ALWAYS);
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

        LocalDictionaryManager.getInstance()
                .deleteWordFromDictionary(currentWord);

        messageLabel.setStyle("-fx-text-fill: #1ad963;");
        messageLabel.setText("Hoàn thành!");

        if (currentWord instanceof EnglishWord) {
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

            Dictionary.wordTypeSet.add(englishWord.getWordType());

        } else if (currentWord instanceof VietnameseWord) {
            VietnameseWord vietnameseWord = new VietnameseWord(wordContent.getText(), wordType.getText());
            for (int i = 0; i < vBox1.getChildren().size(); i++) {
                String content = ((TextArea) vBox1
                        .getChildren()
                        .get(i))
                        .getText();
                if (content.isEmpty()) {
                    continue;
                }
                EnglishWord englishWord = new EnglishWord(content, wordType.getText());
                englishWord.addToVietnameseMeaningsList(vietnameseWord);
                vietnameseWord.addToEnglishMeaningsList(englishWord);
                LocalDictionaryManager.getInstance().insertWordToDictionary(englishWord);
            }
            LocalDictionaryManager.getInstance().insertWordToDictionary(vietnameseWord);
            Dictionary.wordTypeSet.add(vietnameseWord.getWordType());
        }
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
