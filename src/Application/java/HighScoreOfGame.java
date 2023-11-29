package Application.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HighScoreOfGame {

    private static int highestScore1;
    private static File file1 = new File("src/Application/resources/Animation/highscore1.txt");

    private static List<String> highestScore2;
    private static File file2 = new File("src/Application/resources/Animation/highscore2.txt");


    public static int getHighestScore1() {

        try (Scanner scanner = new Scanner(file1)) {
            highestScore1 = scanner.nextInt();
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception or handle it appropriately
        }

        return highestScore1;
    }

    public static void updateHighScore1(int score) {

        // Compare with the current score
        if (score > getHighestScore1()) {
            // Update the high score
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
                writer.write(Integer.toString(score));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> getHighestScore2(MemoryCardGameController.MemoryGame.Difficulty difficulty) {
        List<String> highestScores = new ArrayList<>();
        int n;
        if(difficulty == MemoryCardGameController.MemoryGame.Difficulty.EASY) {
            n = 1;
        } else if(difficulty == MemoryCardGameController.MemoryGame.Difficulty.MEDIUM) {
            n = 2;
        } else {
            n = 3;
        }

        try (Scanner scanner = new Scanner(file2)) {
            for (int i = 0; i < n; i++) {
                if (i == n - 1) {
                    String line = scanner.nextLine();
                    String[] scores = line.split(" ");
                    Collections.addAll(highestScores, scores);
                } else {
                    scanner.nextLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception or handle it appropriately
        }

        return highestScores;
    }

    public static boolean updateHighScore2 (MemoryCardGameController.MemoryGame.Difficulty difficulty, String name, int score, int timeLeft) {
        int n;
        if(difficulty == MemoryCardGameController.MemoryGame.Difficulty.EASY) {
            n = 1;
        } else if(difficulty == MemoryCardGameController.MemoryGame.Difficulty.MEDIUM) {
            n = 2;
        } else {
            n = 3;
        }

        List<String> oldHighestScores = getHighestScore2(difficulty);

        int oldScore = Integer.parseInt(oldHighestScores.get(1));
        int oldTimeLeft = Integer.parseInt(oldHighestScores.get(2));

        if( (score > oldScore && timeLeft <= 0) || (score == 10 && timeLeft > oldTimeLeft) ) {
            System.out.println("Update high score 2");
            try (Scanner scanner = new Scanner(file2)) {
                StringBuilder content = new StringBuilder();

                for (int i = 0; i < 3; i++) {
                    String line = scanner.nextLine();

                    if (i == n - 1) {
                        line = String.format("%s %d %d", name, score, timeLeft);
                    }
                    content.append(line).append("\n");
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2))) {
                    writer.write(content.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
