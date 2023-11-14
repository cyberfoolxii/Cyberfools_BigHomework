package Application.java;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OnlineDictionaryManager extends DictionaryManager {
    private static OnlineDictionaryManager onlineDictionaryManager;
    private SingleWordSource[] sources;

    public static OnlineDictionaryManager getInstance() {
        if (onlineDictionaryManager == null) {
            onlineDictionaryManager = new OnlineDictionaryManager(Dictionary.getInstance());
        }
        return onlineDictionaryManager;
    }
    public SingleWordSource[] getSources() {
        return sources;
    }
    private OnlineDictionaryManager(Dictionary dictionary) {
        super(dictionary);
    }

    /**
     * dịch câu, có thể dịch nhiều câu trong
     * cùng 1 lời gọi phương thức,
     * @param phrases : các câu cần dịch
     * @param from : ngôn ngữ của câu gốc
     * @param to : ngôn ngữ câu đã dịch
     * @return : trả về danh sách tuần tự các câu đã dịch.
     */
    public List<String> phraseTrans(List<String> phrases, String from, String to) throws Exception {
        List<String> results = new ArrayList<>();
        StringBuilder look = new StringBuilder();

        for (int i = 0; i < phrases.size(); i++) {
            look.append("{\"Text\": \"").append(phrases.get(i)).append("\"}");
            if (i != phrases.size() - 1) look.append(", ");
        }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://microsoft-translator-text.p.rapidapi.com/translate?to%5B0%5D=" + to + "&api-version=3.0&from=" + from +"&profanityAction=NoAction&textType=plain"))
                    .header("content-type", "application/json")
                    .header("X-RapidAPI-Key", "1d491d9bb6msh388f4ca7cddf6c4p17c694jsneaec5358edeb")
                    .header("X-RapidAPI-Host", "microsoft-translator-text.p.rapidapi.com")
                    .method("POST", HttpRequest.BodyPublishers.ofString("[" +
                            look +
                            "]"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            Gson gson = new Gson();
            TranslatedPhrase[] translatedPhrases = gson.fromJson(response.body(), TranslatedPhrase[].class);
            for (TranslatedPhrase translatedPhrase : translatedPhrases) {
                for (Translation translation : translatedPhrase.getTranslations()) {
                    results.add(translation.getText());
                }
            }
        return results;
    }

    /**
     * tra định nghĩa đầy đủ của từ tiếng Anh,
     * đồng thời tạo từ mới và các Label với thông tin có được
     * từ API và thêm từ mới đó vào từ điển cục bộ
     * @param whatToLook : từ tiếng anh cần tra
     * cần có quản lí từ điển cục bộ để
     * thêm từ mới về cục bộ
     */
    public void onlineDefinitionLookup(String whatToLook) throws Exception {
            FXMLManager fxmlManager = new FXMLManager();
            VBox vBox = (VBox) Objects.requireNonNull(SceneManager.getInstance().getSceneInSceneList(SceneIndex.HOMEINDEX)).getRoot();
            TabPane tabPane = (TabPane) vBox.getChildren().get(0);
            Tab tab = tabPane.getTabs().get(1);
            VBox vBox1 = (VBox) tab.getContent();
            ScrollPane scrollPane = (ScrollPane) vBox1.getChildren().get(2);
            VBox vBox2 = (VBox) scrollPane.getContent();
            vBox2.getChildren().clear();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.dictionaryapi.dev/api/v2/entries/en/" + whatToLook))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            OnlineWord[] onlineWords = gson.fromJson(response.body(), OnlineWord[].class);

            String audioLink = null;
            for (OnlineWord onlineWord : onlineWords) {
                System.out.println(onlineWord.getWord());

                Label label = null;
                if (onlineWord.getPhonetic() != null) {
                    label = fxmlManager.cloneLabel(onlineWord.getWord() + " " + onlineWord.getPhonetic(), Pos.CENTER, FontPosture.REGULAR, FontWeight.BOLD);
                    label.prefWidthProperty().bind(vBox2.widthProperty());
                    vBox2.getChildren().add(label);
                } else {
                    label = fxmlManager.cloneLabel(onlineWord.getWord() + " " + "(no phonetics)", Pos.CENTER, FontPosture.REGULAR, FontWeight.BOLD);
                    label.prefWidthProperty().bind(vBox2.widthProperty());
                    vBox2.getChildren().add(label);
                }

                System.out.println(onlineWord.getPhonetic());

                for (int i = onlineWord.getPhonetics().length - 1; i >= 0; i--) {
                    if (onlineWord.getPhonetics()[i].getAudio() != null) {
                        audioLink = onlineWord.getPhonetics()[i].getAudio();
                        break;
                    }
                }

                for (OnlineWordMeaning onlineWordMeaning : onlineWord.getMeanings()) {
                    System.out.println(onlineWordMeaning.getPartOfSpeech());

                    Dictionary.wordTypeSet.add(onlineWordMeaning.getPartOfSpeech().toUpperCase());

                    EnglishWord englishWord = new EnglishWord(onlineWord.getWord(), onlineWordMeaning.getPartOfSpeech());
                    if (audioLink != null) {
                        englishWord.setAudioLink(audioLink);
                    }

                    if (onlineWord.getPhonetic() != null) {
                        englishWord.setPhonetic(onlineWord.getPhonetic());
                    } else {
                        englishWord.setPhonetic("(no phonetics)");
                    }


                    label = fxmlManager.cloneLabel(onlineWordMeaning.getPartOfSpeech(), Pos.CENTER, FontPosture.ITALIC, FontWeight.SEMI_BOLD);
                    label.prefWidthProperty().bind(vBox2.widthProperty());
                    vBox2.getChildren().add(label);

                    if(onlineWordMeaning.getDefinitions().length != 0) {
                        vBox2.getChildren().add(fxmlManager.cloneLabel("Definitions:", Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.NORMAL));
                        for (OnlineWordDefinition onlineWordDefinition : onlineWordMeaning.getDefinitions()) {
                            System.out.println(onlineWordDefinition.getDefinition());

                            englishWord.getDefinitions().add(onlineWordDefinition.getDefinition());

                            vBox2.getChildren().add(fxmlManager.cloneLabel(onlineWordDefinition.getDefinition(), Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.NORMAL));
                            if (onlineWordDefinition.getSynonyms().length != 0) {
                                vBox2.getChildren().add(fxmlManager.cloneLabel("Synonyms:", Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                                for (String synonym : onlineWordDefinition.getSynonyms()) {
                                    System.out.print(synonym + ", ");

                                    englishWord.getSynonyms().add(synonym);
                                    vBox2.getChildren().add(fxmlManager.cloneLabel(synonym, Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));

                                }
                            }

                            if (onlineWordDefinition.getAntonyms().length != 0) {
                                vBox2.getChildren().add(fxmlManager.cloneLabel("Antonyms:", Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                                for (String antonym : onlineWordDefinition.getAntonyms()) {
                                    System.out.print(antonym + ", ");

                                    englishWord.getAntonyms().add(antonym);
                                    vBox2.getChildren().add(fxmlManager.cloneLabel(antonym, Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));

                                }
                            }
                            vBox2.getChildren().add(new Separator());
                        }
                    }
                    vBox2.getChildren().add(new Separator());
                    LocalDictionaryManager.getInstance().insertWordToDictionary(englishWord);
                }
            }
            SceneController.currentAudioLink = audioLink;
/*            for (String item : phraseTrans(eDefs, "en", "vi")) {
                vBox1.getChildren().add(fxmlManager.cloneLabel(item, Pos.CENTER, FontPosture.ITALIC, FontWeight.THIN));
            }*/
    }

    /** tra giải nghĩa của từ đơn,
     *
     * @param whatToLook : từ đơn cần tìm giải nghĩa
     * @param from : ngôn ngữ của từ đơn
     * @param to : dịch sang ngôn ngữ gì
     */
    private void DictionaryLookup(String whatToLook, String from, String to) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://microsoft-translator-text.p.rapidapi.com/Dictionary/Lookup?to=" + to + "&api-version=3.0&from=" + from))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "1d491d9bb6msh388f4ca7cddf6c4p17c694jsneaec5358edeb")
                .header("X-RapidAPI-Host", "microsoft-translator-text.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("[\r\n    {\r\n        \"Text\": \"" + whatToLook + "\"\r\n    }\r\n]"))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        sources = gson.fromJson(response.body(), SingleWordSource[].class);
    }

    /** nhiệm vụ của phương thức này là
     *  gọi API dịch từ đơn, tạo từ đơn được dịch
     *  và thêm vào từ điển địa phương Dictionary.
     */
    public void dictionaryLookup(String whatToLook, String from, String to) throws Exception {
        DictionaryLookup(whatToLook, from, to);
        switch (from) {
            case "vi":
                for (SingleWordSource source : sources) {
                    for (SingleWordTranslation translation : source.getTranslations()) {
                        if(!translation.getNormalizedTarget().equals(source.getNormalizedSource())) {
                            Dictionary.wordTypeSet.add(translation.getPosTag().toUpperCase());

                            VietnameseWord vietnameseWord = new VietnameseWord(source.getNormalizedSource(), translation.getPosTag());
                            EnglishWord englishWord = new EnglishWord(translation.getNormalizedTarget(), translation.getPosTag());
                            englishWord.setPhonetic("(no phonetics)");
                            englishWord.addToVietnameseMeaningsList(vietnameseWord);
                            vietnameseWord.addToEnglishMeaningsList(englishWord);
                            LocalDictionaryManager.getInstance().insertWordToDictionary(vietnameseWord);
                            LocalDictionaryManager.getInstance().insertWordToDictionary(englishWord);
                        }
                    }
                }
                break;
            case "en":
                for (SingleWordSource source : sources) {
                    for (SingleWordTranslation translation : source.getTranslations()) {
                        if(!translation.getNormalizedTarget().equals(source.getNormalizedSource())) {
                            Dictionary.wordTypeSet.add(translation.getPosTag().toUpperCase());

                            EnglishWord englishWord = new EnglishWord(source.getNormalizedSource(), translation.getPosTag());
                            englishWord.setPhonetic("(no phonetics)");
                            VietnameseWord vietnameseWord = new VietnameseWord(translation.getNormalizedTarget(), translation.getPosTag());
                            vietnameseWord.addToEnglishMeaningsList(englishWord);
                            englishWord.addToVietnameseMeaningsList(vietnameseWord);
                            LocalDictionaryManager.getInstance().insertWordToDictionary(vietnameseWord);
                            LocalDictionaryManager.getInstance().insertWordToDictionary(englishWord);
                        }
                    }
                }
                break;
        }
    }

}
