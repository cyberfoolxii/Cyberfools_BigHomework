package Application.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    private void insertWordToDictionary(Word word) {
        if (word instanceof VietnameseWord) {
            dictionary.addToVietnameseWordArrayList((VietnameseWord) word);
        } else if (word instanceof EnglishWord) {
            dictionary.addToEnglishWordArrayList((EnglishWord) word);
        }
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
        insertWordToDictionary(newEnglishWord);
        for (VietnameseWord vietnameseWord : newEnglishWord.getVietnameseMeaningsList()) {
            insertWordToDictionary(vietnameseWord);
        }
    }

    public void insertWordFromFile() {
        try {
            File file = new File("src\\Application\\resources\\dictionaries.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                String[] separate = s.split("\t");
                EnglishWord englishWord = new EnglishWord(separate[0], separate[1]);
                for (int i = 2; i < separate.length; i++) {
                    VietnameseWord vietnameseWord = new VietnameseWord(separate[i], separate[1]);
                    vietnameseWord.addToEnglishMeaningsList(englishWord);
                    englishWord.addToVietnameseMeaningsList(vietnameseWord);
                    insertWordToDictionary(vietnameseWord);
                }
                insertWordToDictionary(englishWord);
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
