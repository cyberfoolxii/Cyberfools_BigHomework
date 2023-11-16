package Application.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.io.File;

public class MultipleChoiceGameController implements Initializable {

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
    private VBox myVBox;
    @FXML
    private HBox myHBox;

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
        //initTimerMediaView();

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

/*    private void initTimerMediaView() {
        Media media = new Media(new File("src\\Application\\resources\\Animation\\Countdown.mp4").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        timerMediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::moveToNextQuestion);
    }*/

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
/*        mediaPlayer.stop();
        mediaPlayer.play();*/
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
            questionLabel.setText("Game Over! Your final score is " + score);
            questionLabel.setStyle("-fx-text-fill: #FF0000;");
            for (Node node : myVBox.getChildren()) {
                node.setVisible(false);
                node.setManaged(false);
            }
            
            FXMLManager fxmlManager = new FXMLManager();
            Button menuButton = new Button();
            Button replayButton = new Button();
            menuButton.setText("Menu");
            replayButton.setText("Replay");
            menuButton.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
            replayButton.fontProperty().bind(menuButton.fontProperty());
            myHBox.getChildren().add(menuButton);
            myHBox.getChildren().add(replayButton);
            HBox.setHgrow(menuButton, Priority.ALWAYS);
            HBox.setHgrow(replayButton, Priority.ALWAYS);
            menuButton.prefWidthProperty().bind(myHBox.widthProperty());
            replayButton.prefWidthProperty().bind(myHBox.widthProperty());
            menuButton.setOnAction(event -> backToMenu());
            replayButton.setOnAction(event -> reset());

            Label gameOverLabel = new Label("Game Over! Your final score is " + score);
            gameOverLabel.fontProperty().bind(menuButton.fontProperty());
            HBox gameOverHBox = new HBox(menuButton, replayButton);
            VBox gameOverVBox = new VBox(gameOverLabel, gameOverHBox);

        }
    }

    private void backToMenu() {

    }

    private void reset() {

    }
}
