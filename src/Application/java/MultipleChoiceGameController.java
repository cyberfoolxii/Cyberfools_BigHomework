package Application.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

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
    private Button gamePauseButton;
    @FXML
    private VBox myVBox;
    @FXML
    private HBox myHBox;
    @FXML
    private FlowPane myFlowPane;

    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private int lives;
    private int highestScore;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLManager fxmlManager = new FXMLManager();
        questionLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 25));
        answerButton1.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 20));
        answerButton2.fontProperty().bind(answerButton1.fontProperty());
        answerButton3.fontProperty().bind(answerButton1.fontProperty());
        answerButton4.fontProperty().bind(answerButton1.fontProperty());
        gamePauseButton.fontProperty().bind(answerButton1.fontProperty());
        gamePauseButton.prefWidthProperty().bind(((FlowPane) gamePauseButton.getParent()).widthProperty());

        //initTimerMediaView();

        // Load questions from file
        questions = QuestionReader.readQuestionsFromFile("src/Application/resources/Animation/questions.txt", "src/Application/resources/Animation/answers.txt");

        setInitialGameStates();

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
            setGameOverUI();
        }
    }

    private void setGameOverUI() {
        setGameNodeVisAndManaged(false);
        FXMLManager fxmlManager = new FXMLManager();

        Button menuButton = new Button();
        menuButton.setOnAction(event -> {
            backToMenu();
        });
        menuButton.setText("Menu");
        menuButton.setAlignment(Pos.CENTER);
        menuButton.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        HBox.setHgrow(menuButton, Priority.ALWAYS);
        menuButton.maxWidthProperty().bind(answerButton1.maxWidthProperty());
        menuButton.maxHeightProperty().bind(answerButton1.maxHeightProperty());
        HBox.setMargin(menuButton, new Insets(0, 120, 0, 200));

        Button replayButton = new Button();
        replayButton.setOnAction(event -> {
            reset();
        });
        replayButton.setText("Replay");
        replayButton.setAlignment(Pos.CENTER);
        replayButton.fontProperty().bind(menuButton.fontProperty());
        HBox.setHgrow(replayButton, Priority.ALWAYS);
        replayButton.maxWidthProperty().bind(answerButton1.maxWidthProperty());
        replayButton.maxHeightProperty().bind(answerButton1.maxHeightProperty());
        HBox.setMargin(replayButton, new Insets(0, 200, 0, 0));

        Label gameOverLabel;
        if(score <= highestScore) {
            gameOverLabel = new Label("Game Over! Your final score is " + score);
        } else {
            gameOverLabel = new Label("Congratulation!\nYou have set new highest score: " + score);
            HighScoreOfGame.updateHighScore(score);
        }
        gameOverLabel.prefWidthProperty().bind(questionLabel.widthProperty());
        gameOverLabel.prefHeightProperty().bind(questionLabel.heightProperty());
        gameOverLabel.setTextAlignment(TextAlignment.CENTER);
        gameOverLabel.setAlignment(Pos.CENTER);
        gameOverLabel.fontProperty().bind(questionLabel.fontProperty());

        FlowPane flowPane = new FlowPane(gameOverLabel);
        flowPane.alignmentProperty().bind(myFlowPane.alignmentProperty());
        flowPane.prefWidthProperty().bind(myFlowPane.widthProperty());
        flowPane.prefHeightProperty().bind(myFlowPane.heightProperty());
        VBox.setVgrow(flowPane, Priority.ALWAYS);

        HBox hBox = new HBox(menuButton, replayButton);
        HBox.setMargin(hBox, HBox.getMargin(myHBox));
        VBox.setVgrow(hBox, VBox.getVgrow(myHBox));

        VBox vBox = new VBox(flowPane, hBox);

        myVBox.getChildren().add(vBox);
    }

    private void setGameNodeVisAndManaged(boolean isVisAndManaged) {
        for (Node node : myVBox.getChildren()) {
            node.setVisible(isVisAndManaged);
            node.setManaged(isVisAndManaged);
        }
    }


    private void backToMenu() {
        VBox vBox = (VBox) myVBox.getParent();
        vBox.getChildren().remove(vBox.getChildren().size() - 1);
        for (Node node : vBox.getChildren()) {
            node.setVisible(true);
            node.setManaged(true);
        }

        Button selectGameButton1 = (Button) vBox.lookup("#selectGameButton1");
        if (selectGameButton1 != null) {
            selectGameButton1.setText("Multiple Choice Game\nHighest score: " + HighScoreOfGame.getHighestScore1());
        }
    }

    private void reset() {
        myVBox.getChildren().remove(myVBox.getChildren().size() - 1);
        setInitialGameStates();
        displayQuestion();
        setGameNodeVisAndManaged(true);
    }

    private void setInitialGameStates() {
        // Shuffle questions
        java.util.Collections.shuffle(questions);
        // Initialize game state
        currentQuestionIndex = 0;
        score = 0;
        lives = 3;
        highestScore = HighScoreOfGame.getHighestScore1();
    }

    public void setHighScore(int highScore) {
        this.highestScore = highScore;
    }

    // nhớ sửa responsive cho paused menu
    public void showPausedMenu(ActionEvent event) {
        FXMLManager fxmlManager = new FXMLManager();
        for (Node node : myVBox.getChildren()) {
            node.setVisible(false);
            node.setManaged(false);
        }
        VBox parent = (VBox) fxmlManager.getFXMLInsertedRoot("/FXML Files/MultipleChoiceGameMenu.fxml");

        myVBox.getChildren().add(parent);
        VBox.setVgrow(parent, Priority.ALWAYS);
        parent.maxHeightProperty().bind(myVBox.heightProperty());
        parent.maxWidthProperty().bind(myVBox.widthProperty());
    }
}
