package Application.java;
import Application.java.CustomExceptions.*;
/** class Word đại diện cho một từ. */
public abstract class Word {
    private String wordType; // verb, noun, adjective ...

    protected Word(String wordType) {
        this.wordType = "(" + wordType + ")";
    }

    protected String getWordType() {
        return wordType;
    }

    protected void setWordType(String wordType) {
        this.wordType = wordType;
    }
}

