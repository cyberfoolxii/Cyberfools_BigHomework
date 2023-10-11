package Application.java;

import java.util.ArrayList;

public final class Dictionary {
    private static Dictionary instance;
    private ArrayList<Word> wordArrayList;
    private Dictionary() {
        wordArrayList = new ArrayList<>();
    }

    public static Dictionary getInstance() {
        if (instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }

    public ArrayList<Word> getWordArrayList() {
        return wordArrayList;
    }

    public void setWordArrayList(ArrayList<Word> wordArrayList) {
        this.wordArrayList = wordArrayList;
    }

    public void addToWordArrayList(Word word) {
        wordArrayList.add(word);
    }
}
