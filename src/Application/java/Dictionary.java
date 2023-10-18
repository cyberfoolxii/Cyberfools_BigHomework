package Application.java;

import java.util.ArrayList;

public final class Dictionary {
    private static Dictionary instance;
    private final ArrayList<Word> vietnameseWordsArrayList;
    private final ArrayList<Word> englishWordsArrayList;

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

    public ArrayList<Word> getEnglishWordsArrayList() {
        return englishWordsArrayList;
    }

    public void addToVietnameseWordArrayList(VietnameseWord word) {
        vietnameseWordsArrayList.add(word);
    }

    public ArrayList<Word> getVietnameseWordsArrayList() {
        return vietnameseWordsArrayList;
    }

    public void addToEnglishWordArrayList(EnglishWord word) {
        englishWordsArrayList.add(word);
    }
}
