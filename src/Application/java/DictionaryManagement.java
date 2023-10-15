package Application.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class DictionaryManagement {
    private final Dictionary dictionary;

    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /** sắp xếp danh sách tiếng việt theo thứ tự từ điển. */
    private void sortVietnamese() {
        Collections.sort(dictionary.getVietnameseWordsArrayList());
    }

    /** sắp xếp danh sách tiếng anh theo thứ tự từ điển. */
    private void sortEnglish() {
        Collections.sort(dictionary.getEnglishWordsArrayList());
    }

    /** vì ta đã sắp xếp các danh sách từ cho nên những từ giống nhau
     *  sẽ đứng cạnh nhau, vì thế nếu như gặp từ khác với từ đang xét
     *  thì nhảy từ đang xét lên vị trí j - 1
     *  tuy nhiên những từ giống nhau thì phải gộp giải nghĩa của chúng
     *  trước khi xóa đi
     */
    public void removeWordDuplicates() {
        sortEnglish();
        sortVietnamese();
        HashSet<Integer> deleteWordIndex = new HashSet<>();
        int size = dictionary.getVietnameseWordsArrayList().size();
        ArrayList<VietnameseWord> vietnameseList = dictionary.getVietnameseWordsArrayList();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (vietnameseList.get(i).compareTo(vietnameseList.get(j)) == 0) {
                    deleteWordIndex.add(j);
                } else {
                    i = j - 1;
                    break;
                }
            }
        }

        ArrayList<VietnameseWord> vietnameseWordsResult = new ArrayList<>(vietnameseList.size());
        for (int i = 0; i < size; i++) {
            if (!deleteWordIndex.contains(i)) {
                vietnameseWordsResult.add(vietnameseList.get(i));
            }
        }
        dictionary.setVietnameseWordsArrayList(vietnameseWordsResult);

        deleteWordIndex = new HashSet<>();
        size = dictionary.getEnglishWordsArrayList().size();
        ArrayList<EnglishWord> englishList = dictionary.getEnglishWordsArrayList();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (englishList.get(i).compareTo(englishList.get(j)) == 0) {
                    deleteWordIndex.add(j);
                } else {
                    i = j - 1;
                    break;
                }
            }
        }

        ArrayList<EnglishWord> englishWordsResult = new ArrayList<>(englishList.size());
        for (int i = 0; i < size; i++) {
            if (!deleteWordIndex.contains(i)) {
                englishWordsResult.add(englishList.get(i));
            }
        }
        dictionary.setEnglishWordsArrayList(englishWordsResult);
    }
    /** thêm từ mới vào từ điển.
     *  tự động phân loại đối tượng word tiếng anh hay tiếng việt
     */
    private void insertWordToDictionary(Word word) {
        if (word instanceof VietnameseWord) {
            dictionary.addToVietnameseWordArrayList((VietnameseWord) word);
        } else if (word instanceof EnglishWord) {
            dictionary.addToEnglishWordArrayList((EnglishWord) word);
        }
    }

    /** phương thức này sẽ được tiếp tục chỉnh sửa
     * sao cho phù hợp với phiên bản đồ họa.
     */
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
        scanner.close();
        insertWordToDictionary(newEnglishWord);
        for (VietnameseWord vietnameseWord : newEnglishWord.getVietnameseMeaningsList()) {
            insertWordToDictionary(vietnameseWord);
        }
    }

    /** nạp từ trong file dictionaries.txt cố định vào từ điển.
     * định dạng mỗi dòng :
     * từ_tiếng_anh<tab>loại_từ<tab>từ_tiếng_việt_1<tab>từ_tiếng_việt_2...
     */
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

    /** hiển thị hết mọi từ tiếng anh đã thêm vào từ điển. */
    public void showAllEnglishWords() {
        sortEnglish();
        int wordPos = 1;
        for (EnglishWord e : dictionary.getEnglishWordsArrayList()) {
            System.out.print(wordPos + " | " + e.toString());
            wordPos++;
        }
    }

    /** hiển thị hết mọi từ tiếng việt đã thêm vào từ điển. */
    public void showAllVietnameseWords() {
        sortVietnamese();
        int wordPos = 1;
        for (VietnameseWord v : dictionary.getVietnameseWordsArrayList()) {
            System.out.print(wordPos + " | " + v.toString());
            wordPos++;
        }
    }

    /** muốn tìm từ tiếng việt hay tiếng anh?, đây là phương thức viết chung chung cho tìm cả 2 loại
     *  nên cải tiến lại sau nếu có tùy chọn tìm tiếng việt hoặc tiếng anh riêng rẽ
     */
    public Word dictionaryLookupWord(String whatToLook) {
        /** tìm trong tập tiếng anh của từ điển. */
        int indexFound = Collections.binarySearch(dictionary.getEnglishWordsArrayList(),
                new EnglishWord(whatToLook, null));
        if (indexFound >= 0) {
            return dictionary.getEnglishWordsArrayList().get(indexFound);
        }

        /** nếu không thấy trong tập tiếng anh thì tìm tiếp trong tập tiếng việt. */
        indexFound = Collections.binarySearch(dictionary.getVietnameseWordsArrayList(),
                new VietnameseWord(whatToLook, null));
        if (indexFound >= 0) {
            return dictionary.getVietnameseWordsArrayList().get(indexFound);
        }
        return null;
    }

    /** tương tự
     *  phương thức này chịu ảnh hưởng từ dictionaryLookupWord()
     *  cần được chỉnh sửa lại sau
     */
    public void dictionaryLookup(String whatToLook) {
        Word wordFound = dictionaryLookupWord(whatToLook);
        if (wordFound == null) {
            System.out.println("không tìm thấy từ cần tra!");
            return;
        }
        if (wordFound instanceof EnglishWord) {
            EnglishWord e = (EnglishWord) wordFound;
            System.out.println(e.toString());
            return;
        }
        VietnameseWord v = (VietnameseWord) wordFound;
        System.out.println(v.toString());
    }
}
