package Application.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Hint {
    private static Hint hint;
    private Hint() {
        try {
            FileReader fileReader = new FileReader("src\\Application\\resources\\words_alpha.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                hintList.add(line);
            }
        } catch (IOException e) {

        }
    }

    public static Hint getInstance() {
        if (hint == null) {
            hint = new Hint();
        }
        return hint;
    }

    private final List<String> hintList = new ArrayList<>(370106);

    public List<String> getHintList() {
        return hintList;
    }
}
