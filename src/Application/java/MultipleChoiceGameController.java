package Application.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

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
    @FXML
    private Label scoreLabel;
    @FXML
    private Label livesLabel;

    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private int lives;
    private int highestScore;

    private final MediaPlayer mediaPlayerW = new MediaPlayer(new javafx.scene.media.Media(new File("src/Application/resources/Animation/wrong-answer.mp3").toURI().toString()));
    private final MediaPlayer mediaPlayerR = new MediaPlayer(new javafx.scene.media.Media(new File("src/Application/resources/Animation/right-answer.mp3").toURI().toString()));

    private final MediaPlayer mediaPlayerWG = new MediaPlayer(new javafx.scene.media.Media(new File("src/Application/resources/Animation/game-over.wav").toURI().toString()));
    private final MediaPlayer mediaPlayerLG = new MediaPlayer(new javafx.scene.media.Media(new File("src/Application/resources/Animation/game-completed.wav").toURI().toString()));

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
        scoreLabel.setText("Score: " + score);
        scoreLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 20));
        livesLabel.setText("Lives: " + lives);
        livesLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 20));

        if(mediaPlayerWG.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerWG.stop();
        } else if(mediaPlayerLG.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerLG.stop();
        }
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
            if(mediaPlayerW.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayerW.stop();
            } else if(mediaPlayerR.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayerR.stop();
            }
            mediaPlayerR.play();
            score++;
            scoreLabel.setText("Score: " + score);
            if(score > highestScore) {
                scoreLabel.setStyle("-fx-text-fill: #1ad963;");
            }
            currentQuestionIndex++;

            System.out.println("Correct! Score: " + score);
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
                System.out.println("Right answer: " + questions.get(currentQuestionIndex).getCorrectAnswer());
            } else {
                Collections.shuffle(questions);
                currentQuestionIndex = 0;
                displayQuestion();
                System.out.println("Right answer: " + questions.get(currentQuestionIndex).getCorrectAnswer());
            }
        } else {
            if(mediaPlayerW.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayerW.stop();
            } else if(mediaPlayerR.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayerR.stop();
            }
            lives--;
            if(lives >= 1) mediaPlayerW.play();
            livesLabel.setText("Lives: " + lives);
            if(lives == 1) {
                livesLabel.setStyle("-fx-text-fill: #FF0000;");
            }
            System.out.println("Wrong! Lives: " + lives);
            if (lives > 0) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                    System.out.println("Right answer: " + questions.get(currentQuestionIndex).getCorrectAnswer());
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
            mediaPlayerWG.play();
            gameOverLabel = new Label("Game Over! Your final score is " + score);
            gameOverLabel.setStyle("-fx-text-fill: #FF0000;");
        } else {
            mediaPlayerLG.play();
            gameOverLabel = new Label("Congratulation!\nYou have set new highest score: " + score);
            gameOverLabel.setStyle("-fx-text-fill: #1ad963;");
            HighScoreOfGame.updateHighScore1(score);
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
        if(mediaPlayerWG.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerWG.stop();
        } else if(mediaPlayerLG.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerLG.stop();
        }
        VBox vBox = (VBox) myVBox.getParent();
        vBox.getChildren().remove(vBox.getChildren().size() - 1);
        for (Node node : vBox.getChildren()) {
            node.setVisible(true);
            node.setManaged(true);
        }

        Label infoGame1 = (Label) vBox.lookup("#infoGame1");
        if (infoGame1 != null) {
            infoGame1.setText(" Multiple Choice Game(Trò chơi câu hỏi trắc nghiệm)\n\n Highest score: " + HighScoreOfGame.getHighestScore1() + "."
                    + "\n\n Mô tả: Trò chơi lựa chọn 1 đáp án đúng từ 4 đáp án A, B,\nC, D được hiển thị " +
                    "để điền vào chỗ trống trong câu. Khi\ntrả lời đúng, bạn sẽ được cộng 1 điểm." +
                    " Khi trả lời sai, bạn\nsẽ bị trừ 1 mạng. Trò chơi kết thúc khi bạn trả lời sai 3 lần.");
        }
    }

    private void reset() {
        if(mediaPlayerWG.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerWG.stop();
        } else if(mediaPlayerLG.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerLG.stop();
        }
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
