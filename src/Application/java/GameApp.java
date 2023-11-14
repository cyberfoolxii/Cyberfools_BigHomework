package Application.java;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameApp extends Application {

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private Label questionLabel;
    private ProgressBar progressBar;
    private Label scoreLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Super-Friendly Vocabulary Game");

        // Create questions
        questions = Arrays.asList(
                new Question("What is the capital of France?", Arrays.asList("Berlin", "Madrid", "Paris", "Rome"), "Paris"),
                new Question("Which planet is known as the Red Planet?", Arrays.asList("Venus", "Mars", "Jupiter", "Saturn"), "Mars"),
                new Question("What is the largest mammal in the world?", Arrays.asList("Elephant", "Whale Shark", "Blue Whale", "Giraffe"), "Blue Whale")
        );

        BorderPane borderPane = createBorderPane();
        addQuestionUIComponents(borderPane);

        Scene scene = new Scene(borderPane, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/FXML Files/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane createBorderPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #F4F4F4;");

        return borderPane;
    }

    private void addQuestionUIComponents(BorderPane borderPane) {
        // Question Label
        questionLabel = new Label();
        questionLabel.getStyleClass().add("question-label");
        // Progress Bar
        progressBar = new ProgressBar();
        progressBar.setProgress(0);

        // Score Label
        scoreLabel = new Label("Score: 0");
        scoreLabel.getStyleClass().add("score-label");

        // Answer Buttons
        VBox answerButtons = createAnswerButtons();

        // Next Button
        Button nextButton = new Button("Next");
        nextButton.getStyleClass().add("next-button");
        nextButton.setOnAction(event -> nextQuestion());

        // Layout
        VBox centerVBox = new VBox(20);
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(questionLabel, answerButtons, nextButton);

        HBox bottomHBox = new HBox(20);
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.getChildren().addAll(scoreLabel, progressBar);

        borderPane.setCenter(centerVBox);
        borderPane.setBottom(bottomHBox);

        // Display the first question
        displayQuestion(questions.get(currentQuestionIndex));
    }

    private VBox createAnswerButtons() {
        VBox answerButtons = new VBox(10);
        answerButtons.setAlignment(Pos.CENTER);

        for (int i = 0; i < 4; i++) {
            Button answerButton = new Button();
            answerButton.getStyleClass().add("answer-button");
            answerButton.setOnAction(event -> checkAnswer(answerButton.getText()));

            answerButtons.getChildren().add(answerButton);
        }

        return answerButtons;
    }

    private void displayQuestion(Question question) {
        questionLabel.setText(question.getQuestion());

        List<String> choices = question.getChoices();
        Collections.shuffle(choices);

        VBox answerButtons = (VBox) ((VBox) ((BorderPane) questionLabel.getParent().getParent()).getCenter()).getChildren().get(1);
        for (int i = 0; i < 4; i++) {
            Button answerButton = (Button) answerButtons.getChildren().get(i);
            System.out.println(answerButton);
            answerButton.setText(choices.get(i));
        }
    }

    private void checkAnswer(String selectedAnswer) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (currentQuestion.getCorrectAnswer().equals(selectedAnswer)) {
            score++;
        }

        updateUI();
    }

    private void updateUI() {
        scoreLabel.setText("Score: " + score);
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.size()) {
            displayQuestion(questions.get(currentQuestionIndex));
            progressBar.setProgress((double) currentQuestionIndex / questions.size());
        } else {
            // Game Over
            questionLabel.setText("Game Over! Your final score is: " + score + "/" + questions.size());
            ((VBox) questionLabel.getParent()).getChildren().remove(1); // Remove the VBox containing answer buttons
        }
    }


    private void nextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            updateUI();
        }
    }
}

class Question {
    private String question;
    private List<String> choices;
    private String correctAnswer;

    public Question(String question, List<String> choices, String correctAnswer) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
