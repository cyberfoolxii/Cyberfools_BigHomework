package Application.java;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MemoryCardGameController implements Initializable {
    @FXML
    private GridPane myGridPane;
    private final CardQueue cardQueue = new CardQueue();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < myGridPane.getRowCount(); i++) {
            for (int j = 0; j < myGridPane.getColumnCount(); j++) {
                Card card = new Card();
                card.setCardText("anh cuong dz");
                GridPane.setFillWidth(card.getCardTarget(), true);
                GridPane.setFillHeight(card.getCardTarget(), true);
                GridPane.setMargin(card.getCardTarget(), new Insets(10, 10, 10, 10));
                GridPane.setRowIndex(card.getCardTarget(), i);
                GridPane.setColumnIndex(card.getCardTarget(), j);
                myGridPane.getChildren().add(card.getCardTarget());
            }
        }
    }

    public static class Card {
        private Button cardTarget = new Button();
        private String cardText = "";
        private final RotateTransition flipTransition1 = new RotateTransition();
        private final RotateTransition flipTransition2 = new RotateTransition();

        public Card() {
            FXMLManager fxmlManager = new FXMLManager();
            cardTarget.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 16));
            cardTarget.setMaxWidth(Double.MAX_VALUE);
            cardTarget.setMaxHeight(Double.MAX_VALUE);
            cardTarget.setWrapText(true);
            setUpFlipTransition1();
            setUpFlipTransition2();
            cardTarget.setOnAction(event -> {
                flip();
            });
        }

        public Card(String cardText) {
            this();
            this.cardText = cardText;
        }

        private void setUpFlipTransition1() {
            flipTransition1.setNode(cardTarget);
            flipTransition1.setDuration(Duration.seconds(0.2));
            flipTransition1.setAxis(Rotate.Y_AXIS);
            flipTransition1.setInterpolator(Interpolator.LINEAR);
            flipTransition1.setFromAngle(0);
            flipTransition1.setToAngle(90);
        }

        private void setUpFlipTransition2() {
            flipTransition2.setNode(cardTarget);
            flipTransition2.setDuration(Duration.seconds(0.2));
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

        private void flip() {
            if (!cardTarget.getText().isEmpty()) {
                cardTarget.setScaleX(1.0);
            } else {
                cardTarget.setScaleX(-1.0);
            }
            flipTransition1.play();
            flipTransition1.setOnFinished(event1 -> {
                if (cardTarget.getText().isEmpty()) {
                    cardTarget.setText(cardText);
                } else {
                    cardTarget.setText("");
                }
                flipTransition2.play();
            });
        }
    }
    public static class CardQueue {
        private final LinkedList<Card> cardQueue = new LinkedList<>();
        public void enqueue(Card card) {
            cardQueue.addLast(card);
        }

        public Card dequeue() {
            return cardQueue.removeFirst();
        }
    }
}
