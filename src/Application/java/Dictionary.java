package Application.java;

import java.util.ArrayList;

public final class Dictionary {
    private static Dictionary instance;
    private final ArrayList<VietnameseWord> vietnameseWordsArrayList;
    private final ArrayList<EnglishWord> englishWordsArrayList;

    private Dictionary() {
        vietnameseWordsArrayList = new ArrayList<>();
        englishWordsArrayList = new ArrayList<>();
    }

    public static Dictionary getInstance() {
        if (instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }

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
