package Application.java;

import java.util.ArrayList;

public final class Dictionary {
    private final ArrayList<Word> wordArrayList = new ArrayList<>();
    public ArrayList<Word> getWordArrayList() {
        return wordArrayList;
    }
    public void addToWordArrayList(Word word) {
        wordArrayList.add(word);
    }
}
