package Application.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

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
            private VBox tab0VBox2;
    @FXML
    private Button tab0SpeakButton;
    @FXML
    private VBox tab0DefVbox;
    @FXML
    private HBox updateAndDeleteHBox;
    @FXML
    private Label tabTitle1;
    @FXML
    private Label tabTitle2;
    @FXML
    private Label tabTitle3;
    @FXML
    private Label tabTitle4;
    @FXML
    private Label tabTitle5;
    @FXML
    private Label exitLabel;
    @FXML
    private Button translateButtonTab3;
    @FXML
    private Tab tab5;
    @FXML
    private Button selectGameButton1;
    @FXML
    private Button selectGameButton2;
    @FXML
    private Button selectGameButton3;
    private static Word currentWord;

    static class Cell extends ListCell<String> {
        VBox vBox = new VBox();
        Label label = new Label();
        public Cell(TextField textField, ListView<String> myListView) {
            FXMLManager fxmlManager = new FXMLManager();
            Font font = fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18);
            label.setFont(font);
            label.setWrapText(true);
            label.setAlignment(Pos.CENTER_LEFT);
            label.prefWidthProperty().bind(myListView.widthProperty());
            VBox.setVgrow(label, Priority.ALWAYS);
            vBox.getChildren().add(label);
            this.setOnMouseClicked(mouseEvent -> {
                StringBuilder content = new StringBuilder();
                String[] spl = this.getListView().getSelectionModel().getSelectedItem().split(" ");
                for (int i = 0; i < spl.length; i++) {
                    if (spl[i].charAt(0) >= 65 && spl[i].charAt(0) <= 90) break;
                    content.append(spl[i]).append(" ");
                }
                textField.setText(content.toString().trim());
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (empty) {
                setGraphic(null);
            } else {
                label.setText(item);
                setGraphic(label);
            }
        }
    }

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
        myListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new Cell(tab0searchTextField, myListView);
            }
        });
        tab0searchTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("enter");
                myListView.getItems().clear();
                translate();
            }
        });
        tab0searchTextField.setOnKeyTyped(event -> {
            if ((int) event.getCharacter().charAt(0) != 13 && translateFrom.equals("en")) {
                System.out.println("typed : ");
                myListView.getItems().clear();
                updateAndDeleteHBox.setVisible(false);
                updateAndDeleteHBox.setManaged(false);
                showHints();
            } else if (translateFrom.equals("vi")) {
                myListView.getItems().clear();
                updateAndDeleteHBox.setVisible(false);
                updateAndDeleteHBox.setManaged(false);
                translate();
            }
        });
        updateAndDeleteHBox.setVisible(false);
        updateAndDeleteHBox.setManaged(false);
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

        FXMLManager fxmlManager = new FXMLManager();
        languageMenuButton.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 20));
        onlineCheckBox.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 17));
        tabTitle1.setFont(fxmlManager.cloneQuicksandFont(FontWeight.SEMI_BOLD, 16));
        tabTitle2.fontProperty().bind(tabTitle1.fontProperty());
        tabTitle3.fontProperty().bind(tabTitle1.fontProperty());
        tabTitle4.fontProperty().bind(tabTitle1.fontProperty());
        tabTitle5.fontProperty().bind(tabTitle1.fontProperty());
        exitLabel.fontProperty().bind(tabTitle1.fontProperty());
        tab0searchTextField.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        tab1SearchTextField.fontProperty().bind(tab0searchTextField.fontProperty());
        tab3LanguageMenuButton1.fontProperty().bind(languageMenuButton.fontProperty());
        tab3LanguageMenuButton2.fontProperty().bind(languageMenuButton.fontProperty());
        for (MenuItem item : languageMenuButton.getItems()) {
            ((Label) item.getGraphic()).fontProperty().bind(tabTitle1.fontProperty());
        }
        for (MenuItem item : tab3LanguageMenuButton1.getItems()) {
            ((Label) item.getGraphic()).fontProperty().bind(tabTitle1.fontProperty());
        }
        for (MenuItem item : tab3LanguageMenuButton2.getItems()) {
            ((Label) item.getGraphic()).fontProperty().bind(tabTitle1.fontProperty());
        }
        textArea1.fontProperty().bind(tab0searchTextField.fontProperty());
        textArea2.fontProperty().bind(textArea1.fontProperty());
        translateButtonTab3.fontProperty().bind(tabTitle1.fontProperty());

        selectGameButton1.setText("Multiple Choice Game\nHighest score: " + HighScoreOfGame.getHighestScore1());
    }

    public void switchToGame1(ActionEvent event) {
        for (Node node : ((VBox) tab5.getContent()).getChildren() ) {
            node.setVisible(false);
            node.setManaged(false);
        }

        FXMLManager fxmlManager = new FXMLManager();
        VBox root = (VBox) fxmlManager.getFXMLInsertedRoot("/FXML Files/MultipleChoiceGame.fxml");
        VBox.setVgrow(root, Priority.ALWAYS);
        ((VBox) tab5.getContent()).getChildren().add(root);
    }

    // viet sau
    public void switchToGame2(ActionEvent event) {

    }

    // viet sau
    public void switchToGame3(ActionEvent event) {

    }

    public void updateWord(ActionEvent event) {
        if (myListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        tab0SpeakButton.setVisible(false);
        tab0SpeakButton.setManaged(false);
        String[] spl = myListView.getSelectionModel().getSelectedItem().split(" ");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML Files/NewWordScrollPane.fxml"));
        if ("en".equals(translateFrom)) {
            EnglishWord englishWord = null;
            StringBuilder content = new StringBuilder();
            StringBuilder type = new StringBuilder();
            int i = 0;
            while (i < spl.length) {
                if (spl[i].charAt(0) >= 65 && spl[i].charAt(0) <= 90) break;
                content.append(spl[i]).append(" ");
                i++;
            }

            for (int j = i; j < spl.length; j++) {
                type.append(spl[j]).append(" ");
            }

            int idx = Collections.binarySearch(Dictionary.getInstance()
                            .getEnglishWordsArrayList(),
                    new EnglishWord(content.toString().trim(), type.toString().trim()));
            if (idx >= 0) {
                englishWord = Dictionary
                        .getInstance()
                        .getEnglishWordsArrayList()
                        .get(idx);
            }
            for (Node node : tab0DefVbox.getChildren()) {
                node.setVisible(false);
                node.setManaged(false);
            }
            try {
                ScrollPane scrollPane = fxmlLoader.load();
                UpdateWordController updateWordController = fxmlLoader.getController();
                updateWordController.create(englishWord);
                VBox.setVgrow(scrollPane, Priority.ALWAYS);
                tab0DefVbox.getChildren().add(scrollPane);
                UpdateWordController.setCrossVBox(tab0DefVbox);
            } catch (Exception e) {

            }
        }
        if ("vi".equals(translateFrom)) {
            VietnameseWord vietnameseWord = null;
            StringBuilder content = new StringBuilder();
            StringBuilder type = new StringBuilder();
            int i = 0;
            while (i < spl.length) {
                if (spl[i].charAt(0) >= 65 && spl[i].charAt(0) <= 90) break;
                content.append(spl[i]).append(" ");
                i++;
            }

            for (int j = i; j < spl.length; j++) {
                type.append(spl[j]).append(" ");
            }

            int idx = Collections.binarySearch(Dictionary.getInstance()
                            .getVietnameseWordsArrayList(),
                    new VietnameseWord(content.toString().trim(), type.toString().trim()));
            if (idx >= 0) {
                vietnameseWord = Dictionary
                        .getInstance()
                        .getVietnameseWordsArrayList()
                        .get(idx);
            }
            for (Node node : tab0DefVbox.getChildren()) {
                node.setVisible(false);
                node.setManaged(false);
            }
            try {
                ScrollPane scrollPane = fxmlLoader.load();
                UpdateWordController updateWordController = fxmlLoader.getController();
                updateWordController.create(vietnameseWord);
                VBox.setVgrow(scrollPane, Priority.ALWAYS);
                tab0DefVbox.getChildren().add(scrollPane);
                UpdateWordController.setCrossVBox(tab0DefVbox);
            } catch (Exception e) {

            }
        }
    }


    public void deleteWord(ActionEvent event) {
        if (myListView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String[] spl = myListView.getSelectionModel().getSelectedItem().split(" ");

        StringBuilder content = new StringBuilder();
        StringBuilder type = new StringBuilder();
        int i = 0;
        while (i < spl.length) {
            if (spl[i].charAt(0) >= 65 && spl[i].charAt(0) <= 90) break;
            content.append(spl[i]).append(" ");
            i++;
        }

        for (int j = i; j < spl.length; j++) {
            type.append(spl[j]).append(" ");
        }

        if (translateFrom.equals("en")) {
            int idx = Collections.binarySearch(Dictionary.getInstance()
                            .getEnglishWordsArrayList(),
                    new EnglishWord(content.toString().trim(), type.toString().trim()));
            if (idx >= 0) {
                LocalDictionaryManager.getInstance()
                        .deleteWordFromDictionary(Dictionary
                                .getInstance()
                                .getEnglishWordsArrayList()
                                .get(idx));
            }
        }
        if (translateFrom.equals("vi")) {
            int idx = Collections.binarySearch(Dictionary.getInstance()
                            .getVietnameseWordsArrayList(),
                    new VietnameseWord(content.toString().trim(), type.toString().trim()));
            LocalDictionaryManager
                    .getInstance()
                    .deleteWordFromDictionary(Dictionary
                            .getInstance()
                            .getVietnameseWordsArrayList()
                            .get(idx));
        }
        myListView.getItems().remove(myListView.getSelectionModel().getSelectedItem());
        if (myListView.getItems().isEmpty()) {
            tab0SpeakButton.setVisible(false);
            myListView.setVisible(false);
            updateAndDeleteHBox.setVisible(false);
            updateAndDeleteHBox.setManaged(false);
        }
    }

    //**
    public void addNewWord(ActionEvent event) {
        if (!tab0VBox2.getChildren().get(0).isManaged()) {
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML Files/NewWordScrollPane.fxml"));

        for (Node node : tab0VBox2.getChildren()) {
            node.setVisible(false);
            node.setManaged(false);
        }

        if ("en".equals(translateFrom)) {
            try {
                ScrollPane scrollPane = fxmlLoader.load();
                UpdateWordController updateWordController = fxmlLoader.getController();
                updateWordController.create(new EnglishWord("", ""));
                VBox.setVgrow(scrollPane, Priority.ALWAYS);
                tab0VBox2.getChildren().add(scrollPane);
                UpdateWordController.setCrossVBox(tab0VBox2);
            } catch (Exception e) {

            }
        }
        if ("vi".equals(translateFrom)) {
            try {
                ScrollPane scrollPane = fxmlLoader.load();
                UpdateWordController updateWordController = fxmlLoader.getController();
                updateWordController.create(new VietnameseWord("", ""));
                VBox.setVgrow(scrollPane, Priority.ALWAYS);
                tab0VBox2.getChildren().add(scrollPane);
                UpdateWordController.setCrossVBox(tab0VBox2);
            } catch (Exception e) {

            }
        }
    }


    public void define(ActionEvent event) {
        if (!tab1SearchTextField.getText().isEmpty()) {
            try {
                OnlineDictionaryManager.getInstance().onlineDefinitionLookup(tab1SearchTextField.getText());
                tab1SearchTextField.setPromptText("Definitions?...");
                speakButton1.setVisible(true);
            } catch (Exception e) {
                tab1SearchTextField.setPromptText("Không có kết nối internet!");
            }
        }
        LocalDictionaryManager.getInstance().showAllEnglishWords();
    }

    private void translate() {
        if (!tab0VBox2.getChildren().get(0).isManaged()) {
            UpdateWordController.reset();
        }
        if (tab0searchTextField.getText().isEmpty()) {
            myListView.setVisible(false);
            tab0SpeakButton.setVisible(false);
            return;
        }
        if (onlineCheckBox.isSelected()) {
            try {
                OnlineDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo);
                myListView.setVisible(LocalDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo, splitPane1));
            } catch (Exception e) {

            }
        } else {
            myListView.setVisible(LocalDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo, splitPane1));
        }
    }

    private void showHints() {
        System.out.println("show hints");
        if (tab0searchTextField.getText().length() >= 2) {
            Comparator<String> comparator = new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    Pattern pattern = Pattern.compile("^" + o2);
                    if (pattern.matcher(o1).find()) {
                        return 0;
                    }
                    return o1.compareTo(o2);
                }
            };
            int idx = Collections.binarySearch(Hint.getInstance().getHintList(), tab0searchTextField.getText());
            if (idx >= 0) {
                myListView.getItems().add(Hint.getInstance().getHintList().get(idx));
            }

            idx = Collections.binarySearch(Hint.getInstance().getHintList(), tab0searchTextField.getText(), comparator);
            for (int i = 0; i < 20; i++) {
                if (i + idx < Hint.getInstance().getHintList().size() && idx >= 0 && !myListView.getItems().contains(Hint.getInstance().getHintList().get(i + idx))) {
                    myListView.getItems().add(Hint.getInstance().getHintList().get(i + idx));
                }
            }
        }
        myListView.setVisible(!myListView.getItems().isEmpty());
    }

/*    private void translate() {
*//*        if (!tab0VBox2.getChildren().get(0).isManaged()) {
            UpdateWordController.reset();
        }*//*
        if (tab0searchTextField.getText().isEmpty()) {
            tab0SpeakButton.setVisible(false);
            return;
        }
        myListView.setVisible(false);
        if (onlineCheckBox.isSelected()) {
            if (tab0searchTextField.getText().length() >= 2) {
                Comparator<String> comparator = new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        Pattern pattern = Pattern.compile("^" + o2);
                        if (pattern.matcher(o1).find()) {
                            return 0;
                        }
                        return o1.compareTo(o2);
                    }
                };
                int idx = Collections.binarySearch(Hint.getInstance().getHintList(), tab0searchTextField.getText());
                if (idx >= 0) {
                    myListView.getItems().add(Hint.getInstance().getHintList().get(idx));
                }

                idx = Collections.binarySearch(Hint.getInstance().getHintList(), tab0searchTextField.getText(), comparator);
                for (int i = 0; i < 20; i++) {
                    if (i + idx < Hint.getInstance().getHintList().size() && idx >= 0 && !myListView.getItems().contains(Hint.getInstance().getHintList().get(i + idx))) {
                        myListView.getItems().add(Hint.getInstance().getHintList().get(i + idx));
                    }
                }
                if (!myListView.getItems().isEmpty()) {
                    myListView.setVisible(true);
                }
            }
        } else {
            if(LocalDictionaryManager.getInstance().dictionaryLookup(tab0searchTextField.getText(), translateFrom, translateTo, splitPane1)) {
                myListView.setVisible(true);
            }
        }
    }*/

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
        if (!translateFrom.equals("en")) {
            if (!tab0VBox2.getChildren().get(0).isManaged()) {
                UpdateWordController.reset();
            }
            setTab0Visible(false);
            translateFrom = "en";
            translateTo = "vi";
            languageMenuButton.setText("Tiếng Anh");
            myListView.getItems().clear();
            myListView.setVisible(false);
            tab0SpeakButton.setVisible(false);
        }
    }

    /** sửa sau. */
    public void switchLanguageToVietnamese(ActionEvent event) {
        if (!translateFrom.equals("vi")) {
            if (!tab0VBox2.getChildren().get(0).isManaged()) {
                UpdateWordController.reset();
            }
            setTab0Visible(false);
            translateFrom = "vi";
            translateTo = "en";
            languageMenuButton.setText("Tiếng Việt");
            myListView.getItems().clear();
            myListView.setVisible(false);
            tab0SpeakButton.setVisible(false);
        }
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
        try {
            textArea2.setText("");
            List<String> transList = new ArrayList<>(Arrays.asList(textArea1.getText().split("\n")));
            transList = OnlineDictionaryManager.getInstance().phraseTrans(transList, pTransFrom, pTransTo);
            if (transList != null) {
                for (String s : transList) {
                    textArea2.appendText(s + "\n");
                }
            }
            textArea2.setPromptText("");
        } catch (Exception e) {
            textArea2.setPromptText("Không có kết nối internet");
        }
    }

    public void setTab0Visible(boolean visible) {
        for (Node node : tab0VBox2.getChildren()) {
            node.setVisible(visible);
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

    @FXML
    public void onExitButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát");
        alert.setHeaderText("Nếu bạn thoát trước khi kết thúc game, dữ liệu sẽ không được lưu lại!");
        alert.setContentText("Bạn có chắc chắn muốn thoát không?");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/Application/resources/AppIcon/exit.png"));

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("exit");
            LocalDictionaryManager.getInstance().exportWordToFile();
            stage = (Stage) tab0searchTextField.getScene().getWindow();
            stage.close();
        }
    }
}
