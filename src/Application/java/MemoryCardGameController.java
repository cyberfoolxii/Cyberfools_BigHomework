package Application.java;

import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class MemoryCardGameController implements Initializable {
    @FXML
    private GridPane myGridPane;
    private final List<Card> cardList = new ArrayList<>();
    private final Player player = new Player();
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
            cardList.add(contentCard);
            cardList.add(phoneticCard);
            cardList.add(meaningCard);
        }
        Collections.shuffle(cardList);
        System.out.println(cardList.size());

        for (int row = 0; row < myGridPane.getRowCount(); row++) {
            for (int col = 0; col < myGridPane.getColumnCount(); col++) {
                Card card = cardList.get(row * myGridPane.getColumnCount() + col);
                card.cardTarget.setMaxWidth(Double.MAX_VALUE);
                card.cardTarget.setMaxHeight(Double.MAX_VALUE);
                GridPane.setMargin(card.cardTarget, new Insets(10, 10, 10, 10));
                GridPane.setConstraints(card.cardTarget, col, row);
                myGridPane.getChildren().add(card.cardTarget);
            }
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
            }
            cards.get(0).delayedRevertFlip(true);
            cards.get(1).delayedRevertFlip(true);
            cards.get(2).delayedRevertFlip(true);
            cards.get(0).isFlipped = false;
            cards.get(1).isFlipped = false;
            cards.get(2).isFlipped = false;
            Card.numberOfFlippedCards -= 3;
        }
    }

    public class Player {
        private int score;
        private int lives;
    }

    public class Card {
        private Button cardTarget = new Button();
        private String cardText = "";
        private Integer cardId;
        private boolean isFlipped;
        private final static double FLIP_RATE = 0.5;
        private final static double DELAY_RATE = 2.0;
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
            flipTransition1.setDuration(Duration.seconds(Card.FLIP_RATE));
            flipTransition1.setAxis(Rotate.Y_AXIS);
            flipTransition1.setInterpolator(Interpolator.LINEAR);
            flipTransition1.setFromAngle(0);
            flipTransition1.setToAngle(90);
        }

        private void setUpFlipTransition2() {
            flipTransition2.setNode(cardTarget);
            flipTransition2.setDuration(Duration.seconds(Card.FLIP_RATE));
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

        private void autoFlip() {
            if (isFlipped) {
                numberOfFlippedCards--;
                isFlipped = false;
                revertFlip();
            } else {
                numberOfFlippedCards++;
                isFlipped = true;
                flip();
                MemoryCardGameController.this.triadsCheck();
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
                        new SequentialTransition(new PauseTransition(Duration.seconds(DELAY_RATE)));
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
                        new SequentialTransition(new PauseTransition(Duration.seconds(DELAY_RATE)));
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
            flipTransition3.setDuration(Duration.seconds(Card.FLIP_RATE));
            flipTransition3.setAxis(Rotate.Y_AXIS);
            flipTransition3.setInterpolator(Interpolator.LINEAR);
            flipTransition3.setFromAngle(180);
            flipTransition3.setToAngle(90);
        }

        private void setUpFlipTransition4() {
            flipTransition4.setNode(cardTarget);
            flipTransition4.setDuration(Duration.seconds(Card.FLIP_RATE));
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
