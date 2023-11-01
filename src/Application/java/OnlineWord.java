package Application.java;

public class OnlineWord {
    private String word;
    private String phonetic;
    private Phonetic[] phonetics;
    private OnlineWordMeaning[] meanings;
    public String getPhonetic() {
        return phonetic;
    }

    public String getWord() {
        return word;
    }

    public OnlineWordMeaning[] getMeanings() {
        return meanings;
    }

    public Phonetic[] getPhonetics() {
        return phonetics;
    }
}
