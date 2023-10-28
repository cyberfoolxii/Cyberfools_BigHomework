package Application.java;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    public static final List<String> wordTypeList = new ArrayList<>();

    static {
        wordTypeList.add("NOUN");
        wordTypeList.add("VERB");
        wordTypeList.add("ADJ");
        wordTypeList.add("ADV");
    }

    private final ArrayList<VietnameseWord> vietnameseWordsArrayList = new ArrayList<>();
    private final ArrayList<EnglishWord> englishWordsArrayList = new ArrayList<>();

    public ArrayList<EnglishWord> getEnglishWordsArrayList() {
        return englishWordsArrayList;
    }

    public void addToVietnameseWordArrayList(VietnameseWord word) {
        vietnameseWordsArrayList.add(word);
    }

    public ArrayList<VietnameseWord> getVietnameseWordsArrayList() {
        return vietnameseWordsArrayList;
    }

    public void addToEnglishWordArrayList(EnglishWord word) {
        englishWordsArrayList.add(word);
    }
}
