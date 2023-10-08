package Application.java;
import Application.java.CustomExceptions.*;
/** class Word đại diện cho một từ. */
public class Word implements Comparable<Word> {
    private String englishMeaning;
    private String vietnameseMeaning;
    private String wordType; // verb, noun, adjective ...

    public Word(String englishMeaning, String vietnameseMeaning, String wordType) {
        this.englishMeaning = englishMeaning;
        this.vietnameseMeaning = vietnameseMeaning;
        this.wordType = "(" + wordType + ")";
    }

    public String getEnglishMeaning() {
        return englishMeaning;
    }

    public void setEnglishMeaning(String englishMeaning) throws InappropriateInputException {
        if (englishMeaning == null || englishMeaning.isEmpty()) {
            throw new InappropriateInputException();
        }
        this.englishMeaning = englishMeaning;
    }

    public String getVietnameseMeaning() {
        return vietnameseMeaning;
    }

    public void setVietnameseMeaning(String vietnameseMeaning) throws InappropriateInputException {
        if (vietnameseMeaning == null || vietnameseMeaning.isEmpty()) {
            throw new InappropriateInputException();
        }
        this.vietnameseMeaning = vietnameseMeaning;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) throws InappropriateInputException {
        if (wordType == null || wordType.isEmpty()) {
            throw new InappropriateInputException();
        }
        this.wordType = wordType;
    }

    /** so sánh theo thứ tự từ điển (alphabetical order), lấy thứ tự theo nghĩa tiếng anh. */
    @Override
    public int compareTo(Word word) {
        if (this.englishMeaning.charAt(0) > word.englishMeaning.charAt(0)) {
            return 1;
        } else if (this.englishMeaning.charAt(0) < word.englishMeaning.charAt(0)) {
            return -1;
        }
        if (this.englishMeaning.length() < word.englishMeaning.length()) {
            return -1;
        } else if (this.englishMeaning.length() > word.englishMeaning.length()) {
            return 1;
        } else {
            for (int i = 1; i < this.englishMeaning.length(); i++) {
                if (this.englishMeaning.charAt(i) > word.englishMeaning.charAt(i)) {
                    return 1;
                } else if (this.englishMeaning.charAt(i) < word.englishMeaning.charAt(i)) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
