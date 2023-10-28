package Application.java;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LocalDictionaryManager extends DictionaryManager {

    public LocalDictionaryManager(Dictionary dictionary) {
        super(dictionary);
    }

    public void insertWordFromAPI(Source[] sources, String language) {
        String wordType = "";
        switch (language) {
            case "vi":
                List<VietnameseWord> vList = getDictionary().getVietnameseWordsArrayList();
                for (Source source : sources) {
                    for (Translation translation : source.getTranslations()) {
                        if (!wordType.equals(translation.getPosTag())) {
                            wordType = translation.getPosTag();
                            VietnameseWord newVietnameseWord = new VietnameseWord(source.getDisplaySource(), wordType);
                            getDictionary().addToVietnameseWordArrayList(newVietnameseWord);
                        }
                        VietnameseWord newVietnameseWord = vList.get(vList.size() - 1);
                        EnglishWord englishWord = new EnglishWord(translation.getDisplayTarget(), wordType);
                        englishWord.addToVietnameseMeaningsList(newVietnameseWord);
                        newVietnameseWord.addToEnglishMeaningsList(englishWord);
                        insertWordToDictionary(newVietnameseWord);
                        for (EnglishWord e : newVietnameseWord.getEnglishMeaningsList()) {
                            insertWordToDictionary(e);
                        }
                    }
                }
                break;
            case "en":
                List<EnglishWord> eList = getDictionary().getEnglishWordsArrayList();
                for (Source source : sources) {
                    for (Translation translation : source.getTranslations()) {
                        if (!wordType.equals(translation.getPosTag())) {
                            wordType = translation.getPosTag();
                            EnglishWord newEnglishWord = new EnglishWord(source.getDisplaySource(), wordType);
                            getDictionary().addToEnglishWordArrayList(newEnglishWord);
                        }
                        EnglishWord newEnglishWord = eList.get(eList.size() - 1);
                        VietnameseWord vietnameseWord = new VietnameseWord(translation.getDisplayTarget(), wordType);
                        vietnameseWord.addToEnglishMeaningsList(newEnglishWord);
                        newEnglishWord.addToVietnameseMeaningsList(vietnameseWord);
                        insertWordToDictionary(newEnglishWord);
                        for (VietnameseWord v : newEnglishWord.getVietnameseMeaningsList()) {
                            insertWordToDictionary(v);
                        }
                    }
                }
                break;
        }
    }

    /** BinaryInsert tự thiết kế, có nhiệm vụ chèn từ mới vào từ điển
     *  sao cho từ điển vẫn giữ nguyên thứ tự sắp xếp
     *  không hỗ trợ chèn từ đã tồn tại trong từ điển
     */
    private <T extends Word> void BinaryInsert(List<T> wordList, T word) {
        int lo = 0;
        int hi = wordList.size() - 1;
        int mid = 0;
        while (lo <= hi) {
            mid = lo + (hi - lo)/2;
            if (word.compareTo(wordList.get(mid)) < 0) {
                hi = mid - 1;
            } else if (word.compareTo(wordList.get(mid)) > 0) {
                lo = mid + 1;
            } else break;
        }
        if (lo > hi) {
            if (word.compareTo(wordList.get(mid)) < 0) wordList.add(mid, word);
            else wordList.add(mid + 1, word);
        }
    }

    /** thêm từ mới vào từ điển.
     *  tự động phân loại đối tượng word tiếng anh hay tiếng việt
     */
    private void insertWordToDictionary(Word word) {
        if (word instanceof VietnameseWord) {
            if (getDictionary().getVietnameseWordsArrayList().isEmpty()) {
                getDictionary().getVietnameseWordsArrayList().add((VietnameseWord) word);
                return;
            }
            BinaryInsert(getDictionary().getVietnameseWordsArrayList(), (VietnameseWord) word);
        } else if (word instanceof EnglishWord) {
            if (getDictionary().getEnglishWordsArrayList().isEmpty()) {
                getDictionary().getEnglishWordsArrayList().add((EnglishWord) word);
                return;
            }
            BinaryInsert(getDictionary().getEnglishWordsArrayList(), (EnglishWord) word);
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
            System.out.println("lỗi nhập tệp từ vựng");
        }
    }

    public void exportWordToFile() {
        try {
            File file = new File("src\\Application\\resources\\dictionaries.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (EnglishWord e : getDictionary().getEnglishWordsArrayList()) {
                StringBuilder vMeans = new StringBuilder();
                for (VietnameseWord v : e.getVietnameseMeaningsList()) {
                    vMeans.append("\t").append(v.getWordContent());
                }
                bufferedWriter.write(e.getWordContent() + "\t" + e.getWordType() + vMeans.toString() + "\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.print("lỗi ghi file dictionary.txt");
        }
    }

    /** hiển thị hết mọi từ tiếng anh đã thêm vào từ điển.
     * đây chỉ là phiên bản dành cho command line
     * */
    public void showAllEnglishWords() {
        int wordPos = 1;
        for (EnglishWord e : getDictionary().getEnglishWordsArrayList()) {
            System.out.print(wordPos + " | " + e.getWordContent() + " | " + e.getWordType() + " ");
            for (VietnameseWord v : e.getVietnameseMeaningsList()) {
                System.out.print(v.getWordContent() + ", ");
            }
            System.out.println();
            wordPos++;
        }
    }

    /** hiển thị hết mọi từ tiếng việt đã thêm vào từ điển. */
    public void showAllVietnameseWords() {
        int wordPos = 1;
        for (VietnameseWord v : getDictionary().getVietnameseWordsArrayList()) {
            System.out.print(wordPos + " | " + v.getWordContent() + " | " + v.getWordType() + " ");
            for (EnglishWord e : v.getEnglishMeaningsList()) {
                System.out.print(e.getWordContent() + ", ");
            }
            System.out.println();
            wordPos++;
        }
    }

    private List<Word> dictionaryLookup(List<? extends Word> wordList, String whatToLook) {
        List<Word> resultList = new ArrayList<>();
        for (String wordType : Dictionary.wordTypeList) {
            // tạo đối tượng mới là english word hay vietnamese word đều được, vì ta chỉ so nội dung và loại từ
            int indexFound = Collections.binarySearch(wordList, new EnglishWord(whatToLook, wordType));
            if (indexFound >= 0) resultList.add(wordList.get(indexFound));
        }
        return resultList;
    }
    /** muốn tìm từ tiếng việt hay tiếng anh?, đây là phương thức viết chung chung cho tìm cả 2 loại
     *  nên cải tiến lại sau nếu có tùy chọn tìm tiếng việt hoặc tiếng anh riêng rẽ
     */

    /**
     * cần sửa sau, chưa sử dụng đến tham chiếu to
     */
    public String dictionaryLookup(String whatToLook, String from, String to) {
        StringBuilder result = new StringBuilder();
        switch (from) {
            case "en":
                List<Word> eResultList = dictionaryLookup(getDictionary().getEnglishWordsArrayList(), whatToLook);
                for (Word w: eResultList) {
                    result
                            .append(w.getWordType())
                            .append(": ")
                            .append(w.getWordContent())
                            .append("\n");
                }
                return result.toString();
            case "vi":
                List<Word> vResultList = dictionaryLookup(getDictionary().getVietnameseWordsArrayList(), whatToLook);
                for (Word w: vResultList) {
                    result
                            .append(w.getWordType())
                            .append(": ")
                            .append(w.getWordContent())
                            .append("\n");
                }
                return result.toString();
            default:
        }
        return "Không tìm thấy từ cần tra!";
    }
}
