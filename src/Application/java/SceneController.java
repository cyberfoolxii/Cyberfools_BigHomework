package Application.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SceneController implements Initializable {

    public static String currentAudioLink;
    private String translateFrom = "en";
    private String translateTo = "vi";
    private String pTransFrom = "en";
    private String pTransTo = "vi";
    @FXML
    private TextField tab0searchTextField;
    @FXML
    private TextField tab1SearchTextField;
    @FXML
    private MenuButton languageMenuButton;
    @FXML
    private MenuButton tab3LanguageMenuButton1;
    @FXML
    private MenuButton tab3LanguageMenuButton2;
    @FXML
    private SplitPane splitPane1;
    @FXML
    private ListView<String> myListView;
    @FXML
            private ScrollPane scrollPane1;
    @FXML
            private ScrollPane scrollPane2;
    @FXML
            private ScrollPane scrollPane3;
    @FXML
    private ScrollPane pTransScrollPane1;
    @FXML
    private ScrollPane pTransScrollPane2;
    @FXML
            private VBox vBox4;
    @FXML
            private Button speakButton1;
    @FXML
            private TextArea textArea1;
    @FXML
            private TextArea textArea2;
    @FXML
            private CheckBox onlineCheckBox;
    @FXML
            private VBox tab1VBox2;
    private static Word currentWord;

    public static void setCurrentWord(Word currentWord) {
        SceneController.currentWord = currentWord;
    }

    public static Word getCurrentWord() {
        return currentWord;
    }


    private void showAllWords() {
        LocalDictionaryManager.getInstance().showAllWords(vBox4);
    }

    public void showAllWords(ActionEvent event) {
        showAllWords();
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

        scrollPane1.setFitToWidth(true);
        scrollPane2.setFitToWidth(true);
        scrollPane3.setFitToWidth(true);
        scrollPane1.setFitToHeight(true);
        scrollPane2.setFitToHeight(true);
        scrollPane3.setFitToHeight(true);
        pTransScrollPane1.setFitToWidth(true);
        pTransScrollPane2.setFitToWidth(true);
        pTransScrollPane1.setFitToHeight(true);
        pTransScrollPane2.setFitToHeight(true);
    }

    public void updateWord(ActionEvent event) {
        if (myListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] spl = myListView.getSelectionModel().getSelectedItem().split(" ");
        int idx = Collections.binarySearch(Dictionary.getInstance()
                        .getEnglishWordsArrayList(),
                new EnglishWord(spl[0], spl[1]));
        EnglishWord englishWord = null;
        if (idx >= 0) {
            englishWord = Dictionary
                    .getInstance()
                    .getEnglishWordsArrayList()
                    .get(idx);
            LocalDictionaryManager.getInstance()
                    .deleteWordFromDictionary(englishWord);
        }
        FXMLManager fxmlManager = new FXMLManager();
        //tab1VBox2.getChildren().clear();
        for (Node node : tab1VBox2.getChildren()) {
            //node.setVisible(false);
            node.setManaged(false);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML Files/NewWordScrollPane.fxml"));
        try {
            ScrollPane scrollPane = (ScrollPane) fxmlLoader.load();
            UpdateWordController updateWordController = fxmlLoader.getController();
            updateWordController.create(englishWord);
            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            tab1VBox2.getChildren().add(scrollPane);
            UpdateWordController.setCrossVBox(tab1VBox2);
        } catch (Exception e) {

        }
    }

    public void deleteWord(ActionEvent event) {
        if (myListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] spl = myListView.getSelectionModel().getSelectedItem().split(" ");
        int idx = Collections.binarySearch(Dictionary.getInstance()
                        .getEnglishWordsArrayList(),
                new EnglishWord(spl[0], spl[1]));
        if (idx >= 0) {
            LocalDictionaryManager.getInstance()
                    .deleteWordFromDictionary(Dictionary
                            .getInstance()
                            .getEnglishWordsArrayList()
                            .get(idx));
        } else {
            idx = Collections.binarySearch(Dictionary.getInstance()
                            .getVietnameseWordsArrayList(),
                    new VietnameseWord(spl[0], spl[1]));
            LocalDictionaryManager
                    .getInstance()
                    .deleteWordFromDictionary(Dictionary
                            .getInstance()
                            .getVietnameseWordsArrayList()
                            .get(idx));
        }
        myListView.getItems().remove(myListView.getSelectionModel().getSelectedItem());
        if (myListView.getItems().isEmpty()) {
            myListView.setVisible(false);
        }
    }

    //**
    public void addNewWord(ActionEvent event) {
        if (!tab1VBox2.getChildren().get(0).isManaged()) {
            return;
        }
        FXMLManager fxmlManager = new FXMLManager();
        //tab1VBox2.getChildren().clear();
        for (Node node : tab1VBox2.getChildren()) {
            //node.setVisible(false);
            node.setManaged(false);
        }
        ScrollPane scrollPane = (ScrollPane) fxmlManager.getFXMLInsertedRoot("/FXML Files/NewWordScrollPane.fxml");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        tab1VBox2.getChildren().add(scrollPane);
        UpdateWordController.setCrossVBox(tab1VBox2);
    }


    public void define(ActionEvent event) {
        if (!tab1SearchTextField.getText().isEmpty()) {
            OnlineDictionaryManager.getInstance().onlineDefinitionLookup(tab1SearchTextField.getText());
            speakButton1.setVisible(true);
        }
        LocalDictionaryManager.getInstance().showAllEnglishWords();
    }

    private void translate() {
        if (!tab1VBox2.getChildren().get(0).isManaged()) {
            UpdateWordController.reset();
        }
        if (tab0searchTextField.getText().isEmpty()) return;
        myListView.setVisible(false);
        if (onlineCheckBox.isSelected()) {
            OnlineDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo);
            if(LocalDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo, splitPane1)) {
                myListView.setVisible(true);
            }
        } else {
            if(LocalDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo, splitPane1)) {
                myListView.setVisible(true);
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

    public void speak1(ActionEvent event) {
        SpeechManager speechManager = new SpeechManager();
        if (currentAudioLink != null && !currentAudioLink.isEmpty()) speechManager.speak(currentAudioLink);
    }

    /** sửa sau. */
    public void switchLanguageToEnglish(ActionEvent event) {
        translateFrom = "en";
        translateTo = "vi";
        languageMenuButton.setText("Tiếng Anh");
        myListView.getItems().clear();
        myListView.setVisible(false);
    }

    /** sửa sau. */
    public void switchLanguageToVietnamese(ActionEvent event) {
        translateFrom = "vi";
        translateTo = "en";
        languageMenuButton.setText("Tiếng Việt");
        myListView.getItems().clear();
        myListView.setVisible(false);
    }

    public void switchPLanguageToEnglish1(ActionEvent event) {
        pTransFrom = "en";
        pTransTo = "vi";
        tab3LanguageMenuButton1.setText("Tiếng Anh");
        tab3LanguageMenuButton2.setText("Tiếng Việt");
    }

    public void switchPLanguageToVietnamese1(ActionEvent event) {
        pTransFrom = "vi";
        pTransTo = "en";
        tab3LanguageMenuButton1.setText("Tiếng Việt");
        tab3LanguageMenuButton2.setText("Tiếng Anh");
    }

    public void switchPLanguageToEnglish2(ActionEvent event) {
        pTransFrom = "vi";
        pTransTo = "en";
        tab3LanguageMenuButton1.setText("Tiếng Việt");
        tab3LanguageMenuButton2.setText("Tiếng Anh");
    }

    public void switchPLanguageToVietnamese2(ActionEvent event) {
        pTransFrom = "en";
        pTransTo = "vi";
        tab3LanguageMenuButton1.setText("Tiếng Anh");
        tab3LanguageMenuButton2.setText("Tiếng Việt");
    }

    public void switchSides(ActionEvent event) {
        String temp = pTransFrom;
        pTransFrom = pTransTo;
        pTransTo = temp;
        System.out.println(pTransFrom + " " + pTransTo);
        temp = tab3LanguageMenuButton1.getText();
        tab3LanguageMenuButton1.setText(tab3LanguageMenuButton2.getText());
        tab3LanguageMenuButton2.setText(temp);
        if (!textArea2.getText().isEmpty()) {
            temp = textArea1.getText();
            textArea1.setText(textArea2.getText());
            textArea2.setText(temp);
        }
    }

    public void phraseTranslate(ActionEvent event) {
        if (textArea1.getText().isEmpty()) return;
        textArea2.setText("");
        List<String> transList = new ArrayList<>(Arrays.asList(textArea1.getText().split("\n")));
        transList = OnlineDictionaryManager.getInstance().phraseTrans(transList, pTransFrom, pTransTo);
        if (transList != null) {
            for (String s : transList) {
                textArea2.appendText(s + "\n");
            }
        }
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
