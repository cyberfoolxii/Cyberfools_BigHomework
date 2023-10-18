package Application.java;

import java.util.ArrayList;

/** EnglishWord đại diện cho một từ tiếng anh
 * thật vậy, một từ tiếng anh có thể dịch sang tiếng việt theo nhiều nghĩa
 */
public class EnglishWord extends Word {
    private String english;
    private ArrayList<VietnameseWord> vietnameseMeaningsList;
    public EnglishWord(String english, String wordType) {
        super(wordType);
        this.english = english;
        vietnameseMeaningsList = new ArrayList<>();
    }

    public String getWordContent() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public void addToVietnameseMeaningsList(VietnameseWord vietnameseMeaning) {
        vietnameseMeaningsList.add(vietnameseMeaning);
    }

    public ArrayList<VietnameseWord> getVietnameseMeaningsList() {
        return vietnameseMeaningsList;
    }

    public void setVietnameseMeaningsList(ArrayList<VietnameseWord> vietnameseMeaningsList) {
        if (!vietnameseMeaningsList.isEmpty()) {
            this.vietnameseMeaningsList = vietnameseMeaningsList;
        }
    }

    @Override
    public int compareTo(Word englishWord) {
        EnglishWord e = (EnglishWord) englishWord;
        return english.compareTo(e.english);
    }

    @Override
    public String toString() {
        StringBuilder vietnameseResults = new StringBuilder();
        for (VietnameseWord v : vietnameseMeaningsList) {
            vietnameseResults.append(v.getWordContent()).append("\n");
        }
        return vietnameseResults.toString();
    }
}
