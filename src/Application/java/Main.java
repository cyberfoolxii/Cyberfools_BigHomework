package Application.java;

import com.google.gson.Gson;

import java.io.IOException;
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
        LocalDictionaryManager.getInstance().insertWordFromFile();
        LocalDictionaryManager.getInstance().showAllEnglishWords();
        System.out.println("-------------------------------------------");
        LocalDictionaryManager.getInstance().showAllVietnameseWords();
        LocalDictionaryManager.getInstance().exportWordToFile();
    }
}
