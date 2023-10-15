package Application.java;

import Application.java.CustomExceptions.InappropriateInputException;

import java.util.ArrayList;

public class EnglishWord extends Word implements Comparable<EnglishWord> {
    private String english;
    private final ArrayList<VietnameseWord> vietnameseMeaningsList;
    public EnglishWord(String english, String wordType) {
        super(wordType);
        this.english = english;
        vietnameseMeaningsList = new ArrayList<>();
    }

    public String getEnglish() {
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

    @Override
    public int compareTo(EnglishWord englishWord) {
        return english.compareTo(englishWord.english);
    }
}
