package Application.java;

import java.util.Collections;
import java.util.Scanner;

public class DictionaryManagement {
    private final Dictionary dictionary;

    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
    private void sortVietnamese() {
        Collections.sort(dictionary.getVietnameseWordsArrayList());
    }

    private void sortEnglish() {
        Collections.sort(dictionary.getEnglishWordsArrayList());
    }
    public void insertWordFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insert new English word : ");
        String english = scanner.nextLine();
        System.out.print("Insert word type : ");
        String wordType = scanner.nextLine();
        EnglishWord newEnglishWord = new EnglishWord(english, wordType);
        System.out.println("Insert vietnamese meanings :");
        while (scanner.hasNext()) {
            String vietnamese = scanner.nextLine();
            if (".".equals(vietnamese)) break;
            VietnameseWord vietnameseWord = new VietnameseWord(vietnamese, wordType);
            vietnameseWord.addToEnglishMeaningsList(newEnglishWord);
            newEnglishWord.addToVietnameseMeaningsList(vietnameseWord);
        }
        dictionary.addToEnglishWordArrayList(newEnglishWord);
        for (VietnameseWord vietnameseWord : newEnglishWord.getVietnameseMeaningsList()) {
            dictionary.addToVietnameseWordArrayList(vietnameseWord);
        }
    }

    public void insertWordFromFile() {

    }

    public void showAllEnglishWords() {
        sortEnglish();
        int wordPos = 1;
        for (EnglishWord word : dictionary.getEnglishWordsArrayList()) {
            System.out.print(wordPos + " | " + word.getEnglish()
                    + " | " + word.getWordType() + " | ");
            for (VietnameseWord item : word.getVietnameseMeaningsList()) {
                System.out.print(item.getVietnamese() + ", ");
            }
            System.out.println();
            wordPos++;
        }
    }

    public void showAllVietnameseWords() {
        sortVietnamese();
        int wordPos = 1;
        for (VietnameseWord word : dictionary.getVietnameseWordsArrayList()) {
            System.out.print(wordPos + " | " + word.getVietnamese() + " | "
             + word.getWordType() + " | ");
            for (EnglishWord item : word.getEnglishMeaningsList()) {
                System.out.print(item.getEnglish() + ", ");
            }
            System.out.println();
            wordPos++;
        }
    }
}
