package Application.java;

public class Translation {
    private String normalizedTarget;
    private String displayTarget;
    private String posTag;
    private String prefixWord;
    private BackTranslation[] backTranslations;

    public String getDisplayTarget() {
        return displayTarget;
    }

    public String getNormalizedTarget() {
        return normalizedTarget;
    }

    public String getPosTag() {
        return posTag;
    }

    public String getPrefixWord() {
        return prefixWord;
    }

    public BackTranslation[] getBackTranslations() {
        return backTranslations;
    }
}
