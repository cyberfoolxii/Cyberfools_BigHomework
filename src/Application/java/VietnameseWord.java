package Application.java;

import java.util.ArrayList;

public class VietnameseWord extends Word implements Comparable<VietnameseWord> {
    private String vietnamese;
    private final ArrayList<EnglishWord> englishMeaningsList;
    public VietnameseWord(String vietnamese, String wordType) {
        super(wordType);
        this.vietnamese = vietnamese;
        englishMeaningsList = new ArrayList<>();
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        this.vietnamese = vietnamese;
    }

    public void addToEnglishMeaningsList(EnglishWord englishMeaning) {
        englishMeaningsList.add(englishMeaning);
    }

    public ArrayList<EnglishWord> getEnglishMeaningsList() {
        return englishMeaningsList;
    }

    @Override
    public int compareTo(VietnameseWord vietnameseWord) {
        return vietnamese.compareTo(vietnameseWord.vietnamese);
    }
}
