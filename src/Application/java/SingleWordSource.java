package Application.java;

public class SingleWordSource {
    private String normalizedSource;
    private String displaySource;
    private SingleWordTranslation[] translations;

    public String getDisplaySource() {
        return displaySource;
    }

    public String getNormalizedSource() {
        return normalizedSource;
    }

    public SingleWordTranslation[] getTranslations() {
        return translations;
    }
}
