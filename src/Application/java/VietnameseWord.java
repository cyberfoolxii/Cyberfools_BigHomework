package Application.java;

import java.util.ArrayList;
import java.util.HashSet;

/** VietnameseWord đại diện cho một từ tiếng việt
 * thật vậy, một từ tiếng việt có thể dịch sang tiếng anh theo nhiều nghĩa
 */
public class VietnameseWord extends Word {
    private final HashSet<EnglishWord> englishMeaningsList = new HashSet<>();
    public VietnameseWord(String wordContent, String wordType) {
        super(wordContent, wordType);
    }

    public void addToEnglishMeaningsList(EnglishWord englishMeaning) {
        englishMeaningsList.add(englishMeaning);
    }

    public HashSet<EnglishWord> getEnglishMeaningsList() {
        return englishMeaningsList;
    }

    @Override
    public String toString() {
/*        StringBuilder englishResults = new StringBuilder();
        for (EnglishWord v : englishMeaningsList) {
            englishResults.append(v.getWordContent()).append("\n");
        }
        return englishResults.toString();*/
        return getWordContent() + "\n";
    }
}
