package Application.java;

import java.util.ArrayList;
import java.util.HashSet;

/** EnglishWord đại diện cho một từ tiếng anh
 * thật vậy, một từ tiếng anh có thể dịch sang tiếng việt theo nhiều nghĩa
 */
public class EnglishWord extends Word {
    private String phonetic;
    private final HashSet<String> definitions = new HashSet<>();
    private final HashSet<VietnameseWord> vietnameseMeaningsList = new HashSet<>();
    private final HashSet<String> synonyms = new HashSet<>();
    private final HashSet<String> antonyms = new HashSet<>();
    private String audioLink;

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public HashSet<String> getSynonyms() {
        return synonyms;
    }

    public HashSet<String> getAntonyms() {
        return antonyms;
    }

    public HashSet<String> getDefinitions() {
        return definitions;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public EnglishWord(String wordContent, String wordType) {
        super(wordContent, wordType);
        phonetic = "(no phonetics)";
    }

    public void addToVietnameseMeaningsList(VietnameseWord vietnameseMeaning) {
        vietnameseMeaningsList.add(vietnameseMeaning);
    }

    public HashSet<VietnameseWord> getVietnameseMeaningsList() {
        return vietnameseMeaningsList;
    }

    @Override
    public String toString() {
/*        StringBuilder vietnameseResults = new StringBuilder();
        for (VietnameseWord v : vietnameseMeaningsList) {
            vietnameseResults.append(v.getWordContent()).append("\n");
        }
        return vietnameseResults.toString();*/
        return getWordContent() + "\n";
    }

}
