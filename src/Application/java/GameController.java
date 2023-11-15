package Application.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.io.File;

public class GameController implements Initializable {

    @FXML
    private Label questionLabel;
    @FXML
    private Button answerButton1;
    @FXML
    private Button answerButton2;
    @FXML
    private Button answerButton3;
    @FXML
    private Button answerButton4;
    @FXML
    private MediaView timerMediaView;

    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private int lives;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLManager fxmlManager = new FXMLManager();
        questionLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        answerButton1.fontProperty().bind(questionLabel.fontProperty());
        answerButton2.fontProperty().bind(questionLabel.fontProperty());
        answerButton3.fontProperty().bind(questionLabel.fontProperty());
        answerButton4.fontProperty().bind(questionLabel.fontProperty());
        initTimerMediaView();

        // Load questions from file
        questions = QuestionReader.readQuestionsFromFile("src/Application/resources/Animation/questions.txt", "src/Application/resources/Animation/answers.txt");
        // Shuffle questions
        java.util.Collections.shuffle(questions);
        // Initialize game state
        currentQuestionIndex = 0;
        score = 0;
        lives = 3;

        System.out.println("Right answer: " + questions.get(currentQuestionIndex).getCorrectAnswer());

        // Display the first question
        displayQuestion();
    }

    private void initTimerMediaView() {
        Media media = new Media(new File("src\\Application\\resources\\Animation\\Countdown.mp4").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        timerMediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::moveToNextQuestion);
    }

    private void moveToNextQuestion() {
        lives--;
        System.out.println("Time's up! Lives: " + lives);
        if (lives > 0) {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
                System.out.println("Right answer: " + questions.get(currentQuestionIndex).getCorrectAnswer());
                mediaPlayer.play();
            } else {
                endGame(true);
            }
        } else {
            endGame(false);
        }
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getQuestion());
        String[] options = currentQuestion.getOptions();
        answerButton1.setText(options[0]);
        answerButton2.setText(options[1]);
        answerButton3.setText(options[2]);
        answerButton4.setText(options[3]);
        mediaPlayer.stop();
        mediaPlayer.play();
    }

    @FXML
    private void handleAnswerButtonAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        char selectedAnswer = clickedButton.getText().charAt(0);

        if (selectedAnswer == questions.get(currentQuestionIndex).getCorrectAnswer()) {
            score++;
            currentQuestionIndex++;

            System.out.println("Correct! Score: " + score);
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
                System.out.println("Right answer: " + questions.get(currentQuestionIndex).getCorrectAnswer());
            } else {
                endGame(true);
            }
        } else {
            lives--;

            System.out.println("Wrong! Lives: " + lives);
            if (lives > 0) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                    System.out.println("Right answer: " + questions.get(currentQuestionIndex).getCorrectAnswer());
                } else {
                    endGame(true);
                }
            } else {
                endGame(false);
            }
        }
    }

    private void endGame(boolean playerWon) {
        if (playerWon) {
            System.out.println("Congratulations! You won with a score of " + score);
        } else {
            System.out.println("Game Over! Your final score is " + score);
        }
    }
}
