package Application.java;

import java.util.ArrayList;

/** VietnameseWord đại diện cho một từ tiếng việt
 * thật vậy, một từ tiếng việt có thể dịch sang tiếng anh theo nhiều nghĩa
 */
public class VietnameseWord extends Word {
    private String vietnamese;
    private ArrayList<EnglishWord> englishMeaningsList;
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

    public void setEnglishMeaningsList(ArrayList<EnglishWord> englishMeaningsList) {
        if (!englishMeaningsList.isEmpty()) {
            this.englishMeaningsList = englishMeaningsList;
        }
    }

    @Override
    public int compareTo(Word vietnameseWord) {
        VietnameseWord v = (VietnameseWord) vietnameseWord;
        return vietnamese.compareTo(v.vietnamese);
    }

    @Override
    public String toString() {
        StringBuilder englishResults = new StringBuilder();
        for (EnglishWord v : englishMeaningsList) {
            englishResults.append(v.getEnglish()).append(",");
        }
        return vietnamese + " | " + getWordType() + " | "
                + englishResults + "\n";
    }
}
