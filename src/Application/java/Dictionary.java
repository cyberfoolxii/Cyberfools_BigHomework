package Application.java;

import java.util.ArrayList;
import java.util.HashSet;

public class Dictionary {
    private static Dictionary dictionary;

    public static Dictionary getInstance() {
        if (dictionary == null) {
            dictionary = new Dictionary();
        }
        return dictionary;
    }

    private Dictionary() {

    }

    public static final HashSet<String> wordTypeSet = new HashSet<>();

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
