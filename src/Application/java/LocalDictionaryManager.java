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
        switch (language) {
            case "vi":
                for (Source source : sources) {
                    for (Translation translation : source.getTranslations()) {
                        if(!translation.getNormalizedTarget().equals(source.getNormalizedSource())) {
                            VietnameseWord vietnameseWord = new VietnameseWord(source.getNormalizedSource(), translation.getPosTag());
                            EnglishWord englishWord = new EnglishWord(translation.getNormalizedTarget(), translation.getPosTag());
                            englishWord.addToVietnameseMeaningsList(vietnameseWord);
                            vietnameseWord.addToEnglishMeaningsList(englishWord);
                            insertWordToDictionary(vietnameseWord);
                            insertWordToDictionary(englishWord);
                        }
                    }
                }
                break;
            case "en":
                for (Source source : sources) {
                    for (Translation translation : source.getTranslations()) {
                        if(!translation.getNormalizedTarget().equals(source.getNormalizedSource())) {
                            EnglishWord englishWord = new EnglishWord(source.getNormalizedSource(), translation.getPosTag());
                            VietnameseWord vietnameseWord = new VietnameseWord(translation.getNormalizedTarget(), translation.getPosTag());
                            vietnameseWord.addToEnglishMeaningsList(englishWord);
                            englishWord.addToVietnameseMeaningsList(vietnameseWord);
                            insertWordToDictionary(vietnameseWord);
                            insertWordToDictionary(englishWord);
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
            return;
        }
        T found = wordList.get(mid);
        if (word instanceof EnglishWord && found instanceof EnglishWord) {
            EnglishWord e = (EnglishWord) word;
            EnglishWord foundWord = (EnglishWord) found;
            for (int i = 0; i < e.getVietnameseMeaningsList().size(); i++) {
                foundWord.getVietnameseMeaningsList().add(e.getVietnameseMeaningsList().get(i));
            }
        } else if (word instanceof VietnameseWord && found instanceof VietnameseWord) {
            VietnameseWord v = (VietnameseWord) word;
            VietnameseWord foundWord = (VietnameseWord) found;
            for (int i = 0; i < v.getEnglishMeaningsList().size(); i++) {
                foundWord.getEnglishMeaningsList().add(v.getEnglishMeaningsList().get(i));
            }
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

    /** mặc định xóa trong danh sách các từ tiếng anh. */
    public void deleteWordFromDictionary(String wordContent, String wordType) {
        int idx1 = Collections.binarySearch(getDictionary()
                .getEnglishWordsArrayList(), new EnglishWord(wordContent.toLowerCase(), wordType.toUpperCase()));
        if (idx1 < 0) return;

        EnglishWord e = getDictionary().getEnglishWordsArrayList().get(idx1);
        int idx2 = Collections.binarySearch(getDictionary().getEnglishWordsArrayList(), e);
        if (idx2 >= 0) {
            for (VietnameseWord v : e.getVietnameseMeaningsList()) {
                int index = Collections.binarySearch(getDictionary().getVietnameseWordsArrayList(), v);
                getDictionary().getVietnameseWordsArrayList().get(index).getEnglishMeaningsList().remove(e);
            }
            getDictionary().getEnglishWordsArrayList().remove(e);
        }
    }

    /** tạo từ tiếng anh mới và thêm vào từ điển dựa trên
     *  nội dung từ, loại từ, và danh sách giải nghĩa dạng String
     */
    public void createAndInsertToDictionary(String wordContent, String wordType, List<String> meanings) {
        EnglishWord newEnglishWord = new EnglishWord(wordContent, wordType);
        for (String meaning : meanings) {
            VietnameseWord vietnameseWord = new VietnameseWord(meaning, wordType);
            vietnameseWord.addToEnglishMeaningsList(newEnglishWord);
            newEnglishWord.addToVietnameseMeaningsList(vietnameseWord);
        }
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

                Dictionary.wordTypeSet.add(separate[1].toUpperCase());

                EnglishWord englishWord = new EnglishWord(separate[0].toLowerCase(), separate[1].toUpperCase());
                for (int i = 2; i < separate.length; i++) {
                    VietnameseWord vietnameseWord = new VietnameseWord(separate[i].toLowerCase(), separate[1].toUpperCase());
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
                for (String wordType : Dictionary.wordTypeSet) {
                    int indexFound = Collections.binarySearch(getDictionary().getEnglishWordsArrayList(), new EnglishWord(whatToLook.toLowerCase(), wordType));
                    if (indexFound >= 0) {
                        result.append(wordType).append("\n");
                        for (VietnameseWord v : getDictionary()
                                .getEnglishWordsArrayList()
                                .get(indexFound)
                                .getVietnameseMeaningsList()) {
                            result.append(v.getWordContent()).append("\n");
                        }
                    }
                }
                return result.toString();
            case "vi":
                for (String wordType : Dictionary.wordTypeSet) {
                    int indexFound = Collections.binarySearch(getDictionary().getVietnameseWordsArrayList(), new VietnameseWord(whatToLook.toLowerCase(), wordType));
                    if (indexFound >= 0) {
                        result.append(wordType).append("\n");
                        for (EnglishWord e : getDictionary()
                                .getVietnameseWordsArrayList()
                                .get(indexFound)
                                .getEnglishMeaningsList()) {
                            result.append(e.getWordContent()).append("\n");
                        }
                    }
                }
                return result.toString();
            default:
        }
        return "";
    }
}
