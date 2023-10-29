package Application.java;

import com.google.gson.Gson;

import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

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
        Dictionary dictionary = new Dictionary();
        LocalDictionaryManager l = new LocalDictionaryManager(dictionary);
        l.insertWordFromFile();
        l.showAllEnglishWords();
        System.out.println();
        l.showAllVietnameseWords();
        System.out.println("------------------------------------");
        l.deleteWordFromDictionary("determination", "noun");
        l.showAllEnglishWords();
        System.out.println();
        l.showAllVietnameseWords();
        System.out.println("------------------------------------");
/*        for (String item : Dictionary.wordTypeSet) {
            System.out.println(item);
        }*/
        l.exportWordToFile();
    }
}
