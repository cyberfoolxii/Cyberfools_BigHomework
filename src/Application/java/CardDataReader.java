package Application.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CardDataReader {
    private static List<WordInCard> cards = new ArrayList<>();

    public static List<WordInCard> CardReader() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Application/resources/Animation/gameWordData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    WordInCard card = new WordInCard(parts[0], parts[1], parts[2]);
                    cards.add(card);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(cards);
        return cards;
    }
}

class WordInCard {
    private String word;
    private String pronunciation;
    private String meaning;

    public WordInCard(String word, String pronunciation, String meaning) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.meaning = meaning;
    }

    // getters and setters (you can generate these automatically in most IDEs)

    public String getWordContent() {
        return word;
    }

    public String getPhonetic() {
        return pronunciation;
    }

    public String getVietnameseMeanings() {
        return meaning;
    }
}