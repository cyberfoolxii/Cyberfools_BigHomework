package Application.java;
import Application.java.CustomExceptions.*;

import java.util.List;

/** class Word đại diện cho một từ.
 *  yêu cầu các loại từ kế thừa word phải cài compareTo và toString.
 */
public abstract class Word implements Comparable<Word> {

    /** loại từ : danh từ (noun), tính từ (adjective)... */

    private final String wordContent;
    private final String wordType;

    protected String getWordContent() {
        return wordContent;
    }

    protected String getWordType() {
        return wordType;
    }

    protected Word(String wordContent, String wordType) {
        this.wordType = wordType.toUpperCase();
        this.wordContent = wordContent;
    }

    public abstract String toString();

    @Override
    public int compareTo(Word w) {
        if (this == w) return 0;
        if (this.wordContent.compareTo(w.wordContent) > 0) return 1;
        else if (this.wordContent.compareTo(w.wordContent) < 0) return -1;
        //return this.wordType.substring(0, 3).compareTo(w.wordType.substring(0, 3));
        return this.wordType.compareTo(w.wordType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        return this.compareTo((Word) obj) == 0;
    }
}

