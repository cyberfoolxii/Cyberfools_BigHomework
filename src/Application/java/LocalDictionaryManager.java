package Application.java;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalDictionaryManager extends DictionaryManager {
    private static LocalDictionaryManager localDictionaryManager;

    private LocalDictionaryManager(Dictionary dictionary) {
        super(dictionary);
    }

    public static LocalDictionaryManager getInstance() {
        if (localDictionaryManager == null) {
            localDictionaryManager = new LocalDictionaryManager(Dictionary.getInstance());
        }
        return localDictionaryManager;
    }

    /** BinaryInsert tự thiết kế, có nhiệm vụ chèn từ mới vào từ điển
     *  sao cho từ điển vẫn giữ nguyên thứ tự sắp xếp
     *  không hỗ trợ chèn từ đã tồn tại trong từ điển
     *  thêm nối E-defs
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
            if ("(no phonetics)".equals(foundWord.getPhonetic())) foundWord.setPhonetic(e.getPhonetic());
            for (VietnameseWord v : e.getVietnameseMeaningsList()) {
                foundWord.getVietnameseMeaningsList().add(v);
            }
            for (String def : e.getDefinitions()) {
                foundWord.getDefinitions().add(def);
            }
            for (String synonym : e.getSynonyms()) {
                foundWord.getSynonyms().add(synonym);
            }
            for (String antonym : e.getAntonyms()) {
                foundWord.getAntonyms().add(antonym);
            }
        } else if (word instanceof VietnameseWord && found instanceof VietnameseWord) {
            VietnameseWord v = (VietnameseWord) word;
            VietnameseWord foundWord = (VietnameseWord) found;
            for (EnglishWord e : v.getEnglishMeaningsList()) {
                foundWord.getEnglishMeaningsList().add(e);
            }
        }
    }

    /** thêm từ mới vào từ điển.
     *  tự động phân loại đối tượng word tiếng anh hay tiếng việt
     */
    public void insertWordToDictionary(Word word) {
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

    /** mặc định xóa trong danh sách các từ tiếng anh.
     *  lưu ý, do từ điển lưu trữ dạng Arraylist nên sẽ tối ưu hơn
     *  nếu dùng binarySearch thay vì contains.
     */
    public void deleteWordFromDictionary(Word word) {
        if (word instanceof EnglishWord) {
            int idx = Collections.binarySearch(getDictionary()
                    .getEnglishWordsArrayList(), word);
            if (idx < 0) return;
            EnglishWord e = getDictionary().getEnglishWordsArrayList().get(idx);
            for (VietnameseWord v : e.getVietnameseMeaningsList()) {
                int index = Collections.binarySearch(getDictionary().getVietnameseWordsArrayList(), v);
                getDictionary().getVietnameseWordsArrayList().get(index).getEnglishMeaningsList().remove(e);
            }
            getDictionary().getEnglishWordsArrayList().remove(e);
        } else if (word instanceof VietnameseWord) {
            int idx = Collections.binarySearch(getDictionary()
                    .getVietnameseWordsArrayList(), word);
            if (idx < 0) return;
            VietnameseWord v = getDictionary().getVietnameseWordsArrayList().get(idx);
            for (EnglishWord e : v.getEnglishMeaningsList()) {
                int index = Collections.binarySearch(getDictionary().getEnglishWordsArrayList(), e);
                getDictionary().getEnglishWordsArrayList().get(index).getVietnameseMeaningsList().remove(v);
            }
            getDictionary().getVietnameseWordsArrayList().remove(v);
        }
    }

    /** tạo từ tiếng anh mới và thêm vào từ điển dựa trên
     *  nội dung từ, loại từ, và danh sách giải nghĩa dạng String
     */
    public void createAndInsertWordToDictionary(String wordContent, String pronunciation, String wordType, List<String> meanings) {
        EnglishWord newEnglishWord = new EnglishWord(wordContent, wordType);
        newEnglishWord.setPhonetic(pronunciation);
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
     * từ_tiếng_anh<tab>phát_âm<tab>loại_từ<tab>từ_tiếng_việt_1<tab>từ_tiếng_việt_2...
     * \n định_nghĩa1<tab>định_nghĩa2...
     * \n audio_link (https...)
     */
    public void insertWordFromFile() {
        try {
            File file = new File("src\\Application\\resources\\dictionaries.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                String[] separate = s.split("\t");

                Dictionary.wordTypeSet.add(separate[2].toUpperCase());

                EnglishWord englishWord = new EnglishWord(separate[0].toLowerCase(), separate[2]);
                if (separate[1] != null && !separate[1].isEmpty()) {
                    englishWord.setPhonetic(separate[1]);

                }
                for (int i = 3; i < separate.length; i++) {
                    VietnameseWord vietnameseWord = new VietnameseWord(separate[i].toLowerCase(), separate[2]);
                    vietnameseWord.addToEnglishMeaningsList(englishWord);
                    englishWord.addToVietnameseMeaningsList(vietnameseWord);
                    insertWordToDictionary(vietnameseWord);
                }
                if ((s = bufferedReader.readLine()) != null) {
                    String[] defSeparate = s.split("\t");
                    for (String def : defSeparate) englishWord.getDefinitions().add(def);
                }
                englishWord.setAudioLink(bufferedReader.readLine());
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
                StringBuilder defs = new StringBuilder();
                for (String def : e.getDefinitions()) {
                    defs.append(def).append("\t");
                }
                if (!defs.isEmpty()) defs.deleteCharAt(defs.lastIndexOf("\t"));
                bufferedWriter.write(e.getWordContent()
                        + "\t" + e.getPhonetic()
                        + "\t" + e.getWordType()
                        + vMeans.toString() + "\n"
                        + defs.toString() + "\n"
                        + e.getAudioLink() + "\n");
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
            System.out.print(wordPos + " | " + e.getWordContent()
                    + " | "
                    + e.getPhonetic() + " | "
                    + e.getWordType() + " ");
            for (VietnameseWord v : e.getVietnameseMeaningsList()) {
                System.out.print(v.getWordContent() + ", ");
            }
            System.out.println();
            for (String def : e.getDefinitions()) System.out.println(def);
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

    /** tra xem có từ này trong từ điển hay không
     *  nếu có, hiện toàn bộ thông tin ra màn hình,
     *  nếu không, thì không làm gì cả.
     *  Việc gọi đến API để lấy thông tin không thuộc thẩm quyền
     *  của quản lí địa phương LocalDictionaryManager.
     * @param whatToLook
     * @param from
     * @param to
     * @param tab0SplitPane
     * @return
     */
    public boolean dictionaryLookup(String whatToLook, String from, String to, SplitPane tab0SplitPane) {
        FXMLManager fxmlManager = new FXMLManager();
        VBox vBox = (VBox) tab0SplitPane.getItems().get(0);
        VBox vBox1 = (VBox) tab0SplitPane.getItems().get(1);

        ListView<String> listView = (ListView<String>) vBox.getChildren().get(4);
        listView.getItems().clear();
        vBox1.getChildren().get(0).setVisible(false);

        ScrollPane scrollPane = (ScrollPane) vBox1.getChildren().get(1);
        VBox vBox2 = (VBox) scrollPane.getContent();

        for (String item : Dictionary.wordTypeSet) {
            System.out.println(item);
        }
        System.out.println("------------------------");

        switch (from) {
            case "en":
                List<EnglishWord> englishWordList = new ArrayList<>();

                for (String wordType : Dictionary.wordTypeSet) {
                    int indexFound = Collections.binarySearch(getDictionary().getEnglishWordsArrayList(), new EnglishWord(whatToLook.toLowerCase(), wordType),
                            new Comparator<EnglishWord>() {

                                @Override
                                public int compare(EnglishWord o1, EnglishWord o2) {
                                    Pattern contentPattern = Pattern.compile(o2.getWordContent());
                                    if (contentPattern.matcher(o1.getWordContent()).find()) {
                                        if (o1.getWordType().compareTo(o2.getWordType()) == 0) {
                                            return 0;
                                        }
                                    }
                                    return o1.compareTo(o2);
                                }
                            });
                    if (indexFound >= 0) {
                        englishWordList.add(getDictionary().getEnglishWordsArrayList().get(indexFound));
                    }
                }

                if (englishWordList.isEmpty()) return false;

                for (EnglishWord englishWord : englishWordList) {
                    SceneController.setCurrentWord(englishWord);
                    listView.getItems().add(englishWord.getWordContent() + " " + englishWord.getWordType());
                }

                listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        if (!vBox1.getChildren().get(0).isManaged()) {
                            UpdateWordController.reset();
                        }
                        for (Node node : vBox1.getChildren()) {
                            node.setVisible(true);
                            node.setManaged(true);
                        }
                        vBox2.getChildren().clear();
                        for (EnglishWord englishWord : englishWordList) {
                            if ((englishWord.getWordContent() + " " + englishWord.getWordType()).equals(listView.getSelectionModel().getSelectedItem())) {
                                Label label = fxmlManager.cloneLabel(englishWord.getWordContent() + " " + englishWord.getPhonetic()
                                        , Pos.CENTER, FontPosture.REGULAR, FontWeight.BOLD);
                                label.prefWidthProperty().bind(vBox2.widthProperty());
                                vBox2.getChildren().add(label);

                                label = fxmlManager.cloneLabel(englishWord.getWordType(), Pos.CENTER, FontPosture.ITALIC, FontWeight.SEMI_BOLD);
                                label.prefWidthProperty().bind(vBox2.widthProperty());
                                vBox2.getChildren().add(label);

  /*                              if (englishWord.getVietnameseMeaningsList().isEmpty()) {
                                    OnlineDictionaryManager.getInstance().dictionaryLookup(englishWord.getWordContent(), from, to);
                                }*/
                                if (!englishWord.getVietnameseMeaningsList().isEmpty()) {
                                    vBox2.getChildren().add(fxmlManager.cloneLabel("Giải nghĩa tiếng Việt:", Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.BOLD));
                                    for (VietnameseWord v : englishWord.getVietnameseMeaningsList()) {
                                        vBox2.getChildren().add(fxmlManager.cloneLabel(v.getWordContent()
                                                , Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.NORMAL));
                                    }
                                }

                                vBox2.getChildren().add(new Separator());
                                if (!englishWord.getDefinitions().isEmpty()) {
                                    vBox2.getChildren().add(fxmlManager.cloneLabel("Định nghĩa:", Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.BOLD));
                                    for (String def : englishWord.getDefinitions()) {
                                        vBox2.getChildren().add(fxmlManager.cloneLabel(def, Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.NORMAL));
                                        vBox2.getChildren().add(new Separator());
                                    }
                                }
                                if (!englishWord.getSynonyms().isEmpty()){
                                    vBox2.getChildren().add(fxmlManager.cloneLabel("Từ đồng nghĩa:", Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.BOLD));
                                    for (String synonym : englishWord.getSynonyms()) {
                                        vBox2.getChildren().add(fxmlManager.cloneLabel(synonym, Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                                    }
                                    vBox2.getChildren().add(new Separator());
                                }
                                if (!englishWord.getAntonyms().isEmpty()){
                                    vBox2.getChildren().add(fxmlManager.cloneLabel("Từ trái nghĩa:", Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.BOLD));
                                    for (String antonym : englishWord.getAntonyms()) {
                                        vBox2.getChildren().add(fxmlManager.cloneLabel(antonym, Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                                    }
                                    vBox2.getChildren().add(new Separator());
                                }
                                vBox2.getChildren().add(new Separator());
                                break;
                            }
                        }
                        if (vBox2.getChildren().isEmpty()) {
                            vBox1.getChildren().get(0).setVisible(false);
                        }
                        vBox.getChildren().get(5).setManaged(true);
                        vBox.getChildren().get(5).setVisible(true);
                    }
                });
                break;
            case "vi":

                List<VietnameseWord> vietnameseWordList = new ArrayList<>();

                for (String wordType : Dictionary.wordTypeSet) {
                    int indexFound = Collections.binarySearch(getDictionary().getVietnameseWordsArrayList(), new VietnameseWord(whatToLook.toLowerCase(), wordType));
                    if (indexFound >= 0) {
                        vietnameseWordList.add(getDictionary().getVietnameseWordsArrayList().get(indexFound));
                    }
                }

                if (vietnameseWordList.isEmpty()) return false;

                for (VietnameseWord vietnameseWord : vietnameseWordList) {
                    SceneController.setCurrentWord(vietnameseWord);
                    listView.getItems().add(vietnameseWord.getWordContent() + " " + vietnameseWord.getWordType());
                }


                listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        //
                        if (!vBox1.getChildren().get(0).isManaged()) {
                            UpdateWordController.reset();
                        }
                        for (Node node : vBox1.getChildren()) {
                            node.setVisible(true);
                            node.setManaged(true);
                        }
                        vBox2.getChildren().clear();
                        for (VietnameseWord vietnameseWord : vietnameseWordList) {
                            if ((vietnameseWord.getWordContent() + " " + vietnameseWord.getWordType()).equals(listView.getSelectionModel().getSelectedItem())) {
                                Label label = fxmlManager.cloneLabel(vietnameseWord.getWordContent(), Pos.CENTER, FontPosture.REGULAR, FontWeight.BOLD);
                                label.prefWidthProperty().bind(vBox2.widthProperty());
                                vBox2.getChildren().add(label);

                                label = fxmlManager.cloneLabel(vietnameseWord.getWordType(), Pos.CENTER, FontPosture.ITALIC, FontWeight.SEMI_BOLD);
                                label.prefWidthProperty().bind(vBox2.widthProperty());
                                vBox2.getChildren().add(label);
                                for (EnglishWord e : vietnameseWord.getEnglishMeaningsList()) {
                                    vBox2.getChildren().add(fxmlManager.cloneLabel(e.getWordContent() + " " + e.getPhonetic()
                                            , Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.BOLD));
                                    if (!e.getDefinitions().isEmpty()) {
                                        vBox2.getChildren().add(fxmlManager.cloneLabel("Định nghĩa:", Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.BOLD));
                                        for (String def : e.getDefinitions()) {
                                            vBox2.getChildren().add(fxmlManager.cloneLabel(def
                                                    , Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.BOLD));
                                            vBox2.getChildren().add(new Separator());
                                        }
                                    }
                                    if (!e.getSynonyms().isEmpty()){
                                        vBox2.getChildren().add(fxmlManager.cloneLabel("Từ đồng nghĩa:", Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.BOLD));
                                        for (String synonym : e.getSynonyms()) {
                                            vBox2.getChildren().add(fxmlManager.cloneLabel(synonym, Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                                        }
                                        vBox2.getChildren().add(new Separator());
                                    }
                                    if (!e.getAntonyms().isEmpty()){
                                        vBox2.getChildren().add(fxmlManager.cloneLabel("Từ trái nghĩa:", Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.BOLD));
                                        for (String antonym : e.getAntonyms()) {
                                            vBox2.getChildren().add(fxmlManager.cloneLabel(antonym, Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                                        }
                                        vBox2.getChildren().add(new Separator());
                                    }
                                    vBox2.getChildren().add(new Separator());
                                }
                                vBox2.getChildren().add(new Separator());
                                break;
                            }
                        }
                        if (vBox2.getChildren().isEmpty()) {
                            vBox1.getChildren().get(0).setVisible(false);
                        }
                        vBox.getChildren().get(5).setManaged(true);
                        vBox.getChildren().get(5).setVisible(true);
                    }
                });
                break;
        }
        return true;
    }
    public void showAllWords(VBox vBox) {
        FXMLManager fxmlManager = new FXMLManager();
        vBox.getChildren().clear();
        for (EnglishWord englishWord : getDictionary().getEnglishWordsArrayList()) {
            vBox.getChildren().add(fxmlManager.cloneLabel(englishWord.getWordContent() + " | "
                    + englishWord.getWordType() + " | "
                    + englishWord.getPhonetic(), Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.BOLD));
            for (VietnameseWord v : englishWord.getVietnameseMeaningsList()) {
                vBox.getChildren().add(fxmlManager.cloneLabel(v.getWordContent()
                        , Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.BOLD));
            }
            vBox.getChildren().add(new Separator());
            if (!englishWord.getDefinitions().isEmpty()) {
                vBox.getChildren().add(fxmlManager.cloneLabel("Định nghĩa:", Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.NORMAL));
                for (String def : englishWord.getDefinitions()) {
                    vBox.getChildren().add(fxmlManager.cloneLabel(def, Pos.CENTER_LEFT, FontPosture.REGULAR, FontWeight.NORMAL));
                    vBox.getChildren().add(new Separator());
                }
            }
            if (!englishWord.getSynonyms().isEmpty()){
                vBox.getChildren().add(fxmlManager.cloneLabel("Từ đồng nghĩa:", Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                for (String synonym : englishWord.getSynonyms()) {
                    vBox.getChildren().add(fxmlManager.cloneLabel(synonym, Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                }
                vBox.getChildren().add(new Separator());
            }
            if (!englishWord.getAntonyms().isEmpty()){
                vBox.getChildren().add(fxmlManager.cloneLabel("Từ trái nghĩa:", Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                for (String antonym : englishWord.getAntonyms()) {
                    vBox.getChildren().add(fxmlManager.cloneLabel(antonym, Pos.CENTER_LEFT, FontPosture.ITALIC, FontWeight.THIN));
                }
                vBox.getChildren().add(new Separator());
            }
            vBox.getChildren().add(new Separator());
        }
    }
    public EnglishWord getRandomWord() {
        List<EnglishWord> englishWordList = getDictionary().getEnglishWordsArrayList();
        return englishWordList.get((int) (Math.random() * englishWordList.size()));
    }
}
