package Application.java;
import Application.java.CustomExceptions.*;

/** class Word đại diện cho một từ.
 *  yêu cầu các loại từ kế thừa word phải cài compareTo và toString.
 */
public abstract class Word implements Comparable<Word> {

    /** loại từ : danh từ (noun), tính từ (adjective)... */
    private String wordType;

    protected Word(String wordType) {
        this.wordType = "(" + wordType + ")";
    }

    protected String getWordType() {
        return wordType;
    }

    protected void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public abstract String toString();
}

