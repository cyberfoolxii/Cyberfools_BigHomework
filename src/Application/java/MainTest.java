package Application.java;

import java.util.ArrayList;

public class MainTest {
    public static void main(String[] args) {
        Word w3 = new Word("Apple", "Tao", "noun");
        Word w6 = new Word("Apple", "Buoi", "noun");
        Word w2 = new Word("Pie", "Banh", "noun");
        Word w1 = new Word("Pineapple", "Dua", "noun");
        Word w7 = new Word("Apple", "Tao", "noun");
        Word w4 = new Word("Lingo", "Xin chao", "slang noun");
        Word w5 = new Word("Lingo", "Xin chao", "slang noun");
        Dictionary.getInstance().addToWordArrayList(w1);
        Dictionary.getInstance().addToWordArrayList(w2);
        Dictionary.getInstance().addToWordArrayList(w3);
        Dictionary.getInstance().addToWordArrayList(w4);
        Dictionary.getInstance().addToWordArrayList(w5);
        Dictionary.getInstance().addToWordArrayList(w6);
        Dictionary.getInstance().addToWordArrayList(w7);
        for (int i = 0; i < 10000000; i++) {
            Dictionary.getInstance().addToWordArrayList(new Word(String.valueOf(i), String.valueOf(i), String.valueOf(i)));
        }

        //System.out.println(w4.equals(w5));
        //Dictionary.getInstance().getWordArrayList().remove(w1);
/*        for(Word item : Dictionary.getInstance().getWordArrayList()) {
            System.out.print(item.getEnglishMeaning() + " | " + item.getVietnameseMeaning() + " | " + item.getWordType() + "\n");
        }*/

        long before = System.currentTimeMillis();
        Sort.removeDuplicates(Dictionary.getInstance().getWordArrayList());
        System.out.println(System.currentTimeMillis() - before);
        //Sort.mergeSort(Dictionary.getInstance().getWordArrayList(), aux, 0, Dictionary.getInstance().getWordArrayList().size() - 1);
        //Sort.insertionSort(Dictionary.getInstance().getWordArrayList());
        System.out.println();
/*        for(Word item : Dictionary.getInstance().getWordArrayList()) {
            System.out.print(item.getEnglishMeaning() + " | " + item.getVietnameseMeaning() + " | " + item.getWordType() + "\n");
        }*/
        //Word found = (Word) Sort.binarySearch(Dictionary.getInstance().getWordArrayList(), w1);
        //System.out.println(found.getEnglishMeaning());
    }
}
