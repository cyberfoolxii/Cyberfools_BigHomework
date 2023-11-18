package Application.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HighScoreOfGame {

    private static int highestScore1;
    private static File file1 = new File("src/Application/resources/Animation/highscore1.txt");

    public static int getHighestScore1() {

        try (Scanner scanner = new Scanner(file1)) {
            highestScore1 = scanner.nextInt();
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception or handle it appropriately
        }

        return highestScore1;
    }

    public static void updateHighScore(int score) {

        // Compare with the current score
        if (score > getHighestScore1()) {
            // Update the high score
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
                writer.write(Integer.toString(score));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Display the highest score to the screen
    }
}
