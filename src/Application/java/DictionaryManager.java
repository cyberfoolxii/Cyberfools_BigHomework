package Application.java;

public abstract class DictionaryManager {

    private final Dictionary dictionary;

    protected Dictionary getDictionary() {
        return dictionary;
    }

    public DictionaryManager(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public abstract String dictionaryLookup(String whatToLook, String from, String to);
}
