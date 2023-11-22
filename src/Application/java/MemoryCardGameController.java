package Application.java;

import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MemoryCardGameController implements Initializable {
    @FXML
    private GridPane myGridPane;
    @FXML
    private BorderPane myBorderPane;
    @FXML
            private Button pauseButton;
    @FXML
            private Label livesLabel;
    @FXML
            private Label scoreLabel;
    @FXML
            private Label timeLabel;
    @FXML
            private VBox myVBox;
    public static MediaPlayer mediaPlayer;
    MemoryGame memoryGame = new MemoryGame(MemoryGame.Difficulty.HELL);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (Dictionary.getInstance().getEnglishWordsArrayList().size()
                < (myGridPane.getRowCount() * myGridPane.getColumnCount()) / 3) {
            // hiện ra menu gì đó cho người dùng
            System.out.println("bạn cần có tối thiểu "
                    + ((myGridPane.getRowCount() * myGridPane.getColumnCount()) / 3)
                    + "từ tiếng Anh để bắt đầu!");
            return;
        }

        File file = new File("src\\Application\\resources\\Sound\\DVRST-Close_Eyes.mp3");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        FXMLManager fxmlManager = new FXMLManager();
        pauseButton.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 20));
        livesLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        scoreLabel.fontProperty().bind(livesLabel.fontProperty());
        timeLabel.fontProperty().bind(livesLabel.fontProperty());

        int randomStartIndex = (int) (Math.random()
                * (Dictionary.getInstance().getEnglishWordsArrayList().size()
                - (myGridPane.getRowCount() * myGridPane.getColumnCount()) / 3));
        for (int i = randomStartIndex; i < randomStartIndex + (myGridPane.getRowCount() * myGridPane.getColumnCount()) / 3; i++) {
            Card contentCard = new Card(i, Dictionary.getInstance()
                    .getEnglishWordsArrayList().get(i).getWordContent());

            Card phoneticCard = new Card(i, Dictionary.getInstance()
                    .getEnglishWordsArrayList().get(i).getPhonetic());

            Iterator<VietnameseWord> iterator = Dictionary.getInstance()
                    .getEnglishWordsArrayList().get(i).getVietnameseMeaningsList().iterator();
            String meaning = "";
            if (iterator.hasNext()) {
                meaning = iterator.next().getWordContent();
            }
            Card meaningCard = new Card(i, meaning);
            memoryGame.cardList.add(contentCard);
            memoryGame.cardList.add(phoneticCard);
            memoryGame.cardList.add(meaningCard);
        }
        memoryGame.shuffleCardList();

        for (int row = 0; row < myGridPane.getRowCount(); row++) {
            for (int col = 0; col < myGridPane.getColumnCount(); col++) {
                Card card = memoryGame.cardList.get(row * myGridPane.getColumnCount() + col);
                card.cardTarget.setMaxWidth(Double.MAX_VALUE);
                card.cardTarget.setMaxHeight(Double.MAX_VALUE);
                GridPane.setMargin(card.cardTarget, new Insets(10, 10, 10, 10));
                GridPane.setConstraints(card.cardTarget, col, row);
                myGridPane.getChildren().add(card.cardTarget);
            }
        }
    }

    public void showPausedMenu(ActionEvent event) {
        FXMLManager fxmlManager = new FXMLManager();
        myGridPane.getParent().setVisible(false);
        myGridPane.getParent().setManaged(false);
        VBox vBox = (VBox) fxmlManager.getFXMLInsertedRoot("/FXML Files/MemoryGameMenu.fxml");
        VBox parentVBox = (VBox) myBorderPane.getParent();
        vBox.setMaxWidth(Double.MAX_VALUE);
        vBox.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        parentVBox.getChildren().add(vBox);
    }


    private class MemoryGame {
        private final List<Card> cardList = new ArrayList<>();
        private final Player player = new Player();
        public enum Difficulty {
            EASY,
            MEDIUM,
            HARD,
            HELL
        }

        public MemoryGame(Difficulty difficulty) {
            switch (difficulty) {
                case EASY:
                    Card.flipRate = 0.4;
                    Card.delayRate = 2;
                    break;
                case MEDIUM:
                    Card.flipRate = 0.3;
                    Card.delayRate = 1;
                    break;
                case HARD:
                    Card.flipRate = 0.2;
                    Card.delayRate = 0.5;
                    break;
                case HELL:
                    Card.flipRate = 0.1;
                    Card.delayRate = 0.25;
                    break;
            }
        }

        private void triadsCheck() {
            if (Card.numberOfFlippedCards / 3 > 0) {
                List<Card> cards = new ArrayList<>();
                for (Card card : cardList) {
                    if (card.isFlipped) cards.add(card);
                    if (cards.size() >= 3) break;
                }
                if (cards.get(0).equals(cards.get(1)) && cards.get(1).equals(cards.get(2))) {
                    // lật 3 thẻ đúng thì làm gì đó :v
                    player.gainScore();
                    Card.synchronizedDisappear(cards);
                    return;
                }
                player.loseLive();
                Card.synchronizedRevertFlip(cards);
            }
        }

        private void shuffleCardList() {
            Collections.shuffle(cardList);
        }
    }

    private class Player {
        private int score;
        private int lives;

        private void gainScore() {
            score++;
        }

        private void loseScore() {
            score--;
        }

        private void gainLive() {
            lives++;
        }

        private void loseLive() {
            lives--;
        }
    }

    private class Card {
        private Button cardTarget = new Button();
        private String cardText = "";
        private Integer cardId;
        private boolean isFlipped;
        private static double flipRate = 0.1;
        private static double delayRate = 2.0;
        private static int numberOfFlippedCards = 0;
        private final EventHandler<ActionEvent> cardPickEventHandler = event -> {
            autoFlip();
        };
        private final RotateTransition flipTransition1 = new RotateTransition();
        private final RotateTransition flipTransition2 = new RotateTransition();
        private final RotateTransition flipTransition3 = new RotateTransition();
        private final RotateTransition flipTransition4 = new RotateTransition();

        public Card() {
            FXMLManager fxmlManager = new FXMLManager();
            cardTarget.setScaleX(-1.0);
            cardTarget.setFont(Font.font("Charis SIL", FontWeight.BOLD, 16));
            cardTarget.setTextAlignment(TextAlignment.CENTER);
            cardTarget.setMaxWidth(Double.MAX_VALUE);
            cardTarget.setMaxHeight(Double.MAX_VALUE);
            cardTarget.setWrapText(true);
            setUpFlipTransition1();
            setUpFlipTransition2();
            setUpFlipTransition3();
            setUpFlipTransition4();
            cardTarget.setOnAction(cardPickEventHandler);
        }

        public Card(String cardText) {
            this();
            this.cardText = cardText;
        }

        public Card(String cardText, Font font) {
            this(cardText);
            setCardFont(font);
        }

        public Card(int cardId, String cardText) {
            this(cardText);
            this.cardId = cardId;
        }

        public void setCardFont(Font font) {
            cardTarget.setFont(font);
        }

        private void setUpFlipTransition1() {
            flipTransition1.setNode(cardTarget);
            flipTransition1.setDuration(Duration.seconds(Card.flipRate));
            flipTransition1.setAxis(Rotate.Y_AXIS);
            flipTransition1.setInterpolator(Interpolator.LINEAR);
            flipTransition1.setFromAngle(0);
            flipTransition1.setToAngle(90);
        }

        private void setUpFlipTransition2() {
            flipTransition2.setNode(cardTarget);
            flipTransition2.setDuration(Duration.seconds(Card.flipRate));
            flipTransition2.setAxis(Rotate.Y_AXIS);
            flipTransition2.setInterpolator(Interpolator.LINEAR);
            flipTransition2.setFromAngle(90);
            flipTransition2.setToAngle(180);
        }

        public Button getCardTarget() {
            return cardTarget;
        }

        public void setCardTarget(Button cardTarget) {
            this.cardTarget = cardTarget;
        }

        public void setCardText(String cardText) {
            this.cardText = cardText;
        }

        private static void synchronizedRevertFlip(List<Card> cards) {
            for (Card card : cards) {
                card.isFlipped = false;
                card.delayedRevertFlip(true);
            }
            Card.numberOfFlippedCards -= cards.size();
        }

        private static void synchronizedDisappear(List<Card> cards) {
            SequentialTransition s =
                    new SequentialTransition(new PauseTransition(Duration.seconds(Card.delayRate)));
            s.play();
            s.setOnFinished(event -> {
                for (Card card : cards) {
                    card.isFlipped = false;
                    card.flipTransition3.setOnFinished(event1 -> {});
                    card.flipTransition3.play();
                }
                Card.numberOfFlippedCards -= cards.size();
            });
        }

        private void autoFlip() {
            if (isFlipped) {
                numberOfFlippedCards--;
                isFlipped = false;
                revertFlip();
            } else {
                numberOfFlippedCards++;
                isFlipped = true;
                flip();
                MemoryCardGameController.this.memoryGame.triadsCheck();
            }
            System.out.println("flipped : " + numberOfFlippedCards + " cards");
        }

        private void flip() {
            cardTarget.setOnAction(event -> {

            });

            flipTransition1.setOnFinished(event1 -> {
                cardTarget.setText(cardText);
                flipTransition2.play();
            });
            flipTransition2.setOnFinished(event -> {
                cardTarget.setOnAction(cardPickEventHandler);
            });

            flipTransition1.play();
        }

        private void delayedFlip(boolean isDelayed) {
            if (isDelayed) {
                SequentialTransition s =
                        new SequentialTransition(new PauseTransition(Duration.seconds(delayRate)));
                s.setOnFinished(event -> {
                    flip();
                });
                s.play();
            } else {
                flip();
            }
        }

        private void delayedRevertFlip(boolean isDelayed) {
            if (isDelayed) {
                SequentialTransition s =
                        new SequentialTransition(new PauseTransition(Duration.seconds(delayRate)));
                s.setOnFinished(event -> {
                    revertFlip();
                });
                s.play();
            } else {
                revertFlip();
            }
        }

        private void revertFlip() {
            cardTarget.setOnAction(event -> {

            });
            flipTransition3.setOnFinished(event1 -> {
                cardTarget.setText("");
                flipTransition4.play();
            });
            flipTransition4.setOnFinished(event -> {
                cardTarget.setOnAction(cardPickEventHandler);
            });
            flipTransition3.play();
        }

        private void setUpFlipTransition3() {
            flipTransition3.setNode(cardTarget);
            flipTransition3.setDuration(Duration.seconds(Card.flipRate));
            flipTransition3.setAxis(Rotate.Y_AXIS);
            flipTransition3.setInterpolator(Interpolator.LINEAR);
            flipTransition3.setFromAngle(180);
            flipTransition3.setToAngle(90);
        }

        private void setUpFlipTransition4() {
            flipTransition4.setNode(cardTarget);
            flipTransition4.setDuration(Duration.seconds(Card.flipRate));
            flipTransition4.setAxis(Rotate.Y_AXIS);
            flipTransition4.setInterpolator(Interpolator.LINEAR);
            flipTransition4.setFromAngle(90);
            flipTransition4.setToAngle(0);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Card)) return false;
            return this.cardId.equals(((Card) obj).cardId);
        }
    }
}
