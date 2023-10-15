package Application.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainTest {

    public void start() {
        boolean isRunning = true;
        DictionaryManagement manager = new DictionaryManagement(Dictionary.getInstance());
        while (isRunning) {
            System.out.println("[1] Insert New Word");
            System.out.println("[2] Show All English Words");
            System.out.println("[3] Show All Vietnamese Words");
            System.out.println("[4] Exit");
            System.out.print("Type Your Option : ");
            Scanner scanner = new Scanner(System.in);
            switch (scanner.next()) {
                case "1":
                    manager.insertWordFromCommandline();
                    break;
                case "2":
                    manager.showAllEnglishWords();
                    break;
                case "3":
                    manager.showAllVietnameseWords();
                    break;
                case "4":
                    isRunning = false;
                    break;
            }
        }
    }
    public static void main(String[] args) {
        MainTest program = new MainTest();
        program.start();
    }
}
