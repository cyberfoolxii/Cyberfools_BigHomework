package Application.java;

import java.util.ArrayList;

/** EnglishWord đại diện cho một từ tiếng anh
 * thật vậy, một từ tiếng anh có thể dịch sang tiếng việt theo nhiều nghĩa
 */
public class EnglishWord extends Word {
    private final ArrayList<VietnameseWord> vietnameseMeaningsList = new ArrayList<>();
    public EnglishWord(String wordContent, String wordType) {
        super(wordContent, wordType);
    }

    public void addToVietnameseMeaningsList(VietnameseWord vietnameseMeaning) {
        vietnameseMeaningsList.add(vietnameseMeaning);
    }

    public ArrayList<VietnameseWord> getVietnameseMeaningsList() {
        return vietnameseMeaningsList;
    }

    @Override
    public String toString() {
/*        StringBuilder vietnameseResults = new StringBuilder();
        for (VietnameseWord v : vietnameseMeaningsList) {
            vietnameseResults.append(v.getWordContent()).append("\n");
        }
        return vietnameseResults.toString();*/
        return getWordContent() + "\n";
    }
}
