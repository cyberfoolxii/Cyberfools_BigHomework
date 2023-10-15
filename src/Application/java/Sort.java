package Application.java;

import java.util.ArrayList;
import java.util.HashSet;

public final class Sort {

    public static void removeDuplicates(ArrayList<Word> a) {
        ArrayList<Word> aux = new ArrayList<>(Dictionary.getInstance().getWordArrayList().size());
        for (int i = 0; i < Dictionary.getInstance().getWordArrayList().size(); i++) {
            aux.add(null);
        }
        mergeSort(a, aux, 0, a.size() - 1);
        HashSet<Integer> ignore = new HashSet<>();
        for (int i = 0; i < a.size(); i++) {
            for (int j = i + 1; j < a.size(); j++) {
                if (a.get(i).equals(a.get(j))) {
                    ignore.add(j);
                }
                if (!a.get(i).getEnglishMeaning().equals(a.get(j).getEnglishMeaning())
                        || j == a.size() - 1) {
                    i = j - 1;
                    break;
                }
            }
        }
        ArrayList<Word> result = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            if (!ignore.contains(i)) {
                result.add(a.get(i));
            }
        }
        Dictionary.getInstance().setWordArrayList(result);
    }

    private static boolean less(Word a, Word b) {
        return a.compareTo(b) < 0;
    }

    private static void swap(ArrayList<Word> words, int i, int j) {
        Word mid = words.get(i);
        words.set(i, words.get(j));
        words.set(j, mid);
    }

    public static Word binarySearch(ArrayList<Word> a, Word key) {
        int lo = 0;
        int hi = a.size() - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if (less(a.get(mid), key)) {
                lo = mid + 1;
            } else if (less(key, a.get(mid))) {
                hi = mid - 1;
            } else {
                return a.get(mid);
            }
        }
        return null;
    }

    public static void insertionSort(ArrayList<Word> a) {
        for (int i = 0; i < a.size() - 1; i++) {
            if (!less(a.get(i), a.get(i+1))) {
                swap(a, i, i+1);
                for (int j = i; j >= 1; j--) {
                    if (less(a.get(j), a.get(j-1))) {
                        swap(a, j, j-1);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static void merge(ArrayList<Word> a, ArrayList<Word> aux, int lo, int hi) {
        int mid = lo + (hi - lo)/2;
        int k = lo;
        int i = lo;
        int j = mid + 1;
        for (int z = lo; z <= hi; z++) {
            aux.set(z, a.get(z));
        }
        while (k <= hi) {
            if (i <= mid && j <= hi) {
                if(less(aux.get(j), aux.get(i))) {
                    a.set(k++, aux.get(j++));
                } else {
                    a.set(k++, aux.get(i++));
                }
            } else if (i <= mid) {
                a.set(k++, aux.get(i++));
            } else if (j <= hi) {
                a.set(k++, aux.get(j++));
            } else {
                break;
            }
        }
    }
    public static void mergeSort(ArrayList<Word> a, ArrayList<Word> aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo)/2;
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid + 1, hi);
        if (!less(a.get(mid+1), a.get(mid))) {
            return;
        }
        merge(a, aux, lo, hi);
    }
}