package Application.java;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class GameController {
    @FXML
    private Label wordLabel;
    @FXML
    private TextField answerField;
    @FXML
    private Label resultLabel;

    private String currentWord;
    private String currentAnswer;

    public void initialize() {
        currentWord = "Apple";
        currentAnswer = "apple";
        wordLabel.setText(currentWord);
    }

    @FXML
    private void checkAnswer() {
        String userAnswer = answerField.getText().trim().toLowerCase();

        if (userAnswer.equals(currentAnswer)) {
            resultLabel.setText("Correct!");
        } else {
            resultLabel.setText("Incorrect. Try again.");
        }
    }
}
