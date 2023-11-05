package Application.java;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.CollationElementIterator;
import java.util.*;

public class Main {

    private static int BinarySearch(List<Integer> list, int key) {
        int lo = 0;
        int hi = list.size() - 1;
        int mid = 0;
        while (lo <= hi) {
            mid = lo + (hi - lo)/2;
            if (key < list.get(mid)) {
                hi = mid - 1;
            } else if (key > list.get(mid)) {
                lo = mid + 1;
            }
            else break;
        }
        if (lo > hi) {
            System.out.println("not found");
            if (key < list.get(mid)) list.add(mid, key);
            else list.add(mid + 1, key);
            return -1;
        }
        return mid;
    }
    public static void main(String[] args) {
        //LocalDictionaryManager.getInstance().insertWordFromFile();
        LocalDictionaryManager.getInstance().showAllEnglishWords();
        EnglishWord englishWord = new EnglishWord("fly", "VERB");
        LocalDictionaryManager.getInstance().insertWordToDictionary(englishWord);
        EnglishWord englishWord1 = new EnglishWord("fly", "NOUN");
        LocalDictionaryManager.getInstance().insertWordToDictionary(englishWord1);
        EnglishWord englishWord2 = new EnglishWord("rub", "VERB");
        LocalDictionaryManager.getInstance().insertWordToDictionary(englishWord2);
        LocalDictionaryManager.getInstance().showAllEnglishWords();
        System.out.println("-------------------------------------------");
        LocalDictionaryManager.getInstance().deleteWordFromDictionary(englishWord);
        LocalDictionaryManager.getInstance().deleteWordFromDictionary(englishWord1);

        LocalDictionaryManager.getInstance().showAllEnglishWords();
        //LocalDictionaryManager.getInstance().exportWordToFile();
        Comparator<Word> comparator = new Comparator<>() {
            @Override
            public int compare(Word o1, Word o2) {
                return o1.getWordContent().compareTo(o2.getWordContent());
            }
        };
    }
}
