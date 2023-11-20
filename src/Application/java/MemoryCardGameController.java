package Application.java;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
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
    private List<WordInCard> allCards = CardReader.CardReader();
    private static List<WordInCard> wordCards = new ArrayList<>(10);
    private static List<Integer> wordMapIndex = new ArrayList<>(10);
    private HashMap<String, Integer> wordMap = new HashMap<>(30);
    private static List<Card> flippedCards = new ArrayList<>(3);
    private static boolean gameStarted = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Collections.shuffle(allCards);
        for (int i = 0; i < 30; i++) {
            if(i < 10) {
                wordCards.add(allCards.get(i));
            }
            wordMapIndex.add(i);
        }
        Collections.shuffle(wordMapIndex);

        for (int i = 0; i < 10; i++) {
            wordMap.put(wordCards.get(i).getWord(), wordMapIndex.get(i * 3));
            wordMap.put(wordCards.get(i).getPronunciation(), wordMapIndex.get(i * 3 + 1));
            wordMap.put(wordCards.get(i).getMeaning(), wordMapIndex.get(i * 3 + 2));
        }

        int numRows = myGridPane.getRowCount();
        int numCols = myGridPane.getColumnCount();

        for (int i = 0; i < 10; i++) {
            int row = i / numCols;
            int col = i % numCols;

            Card card = new Card();
            card.setCardText(wordCards.get(i).getWord());
            GridPane.setFillWidth(card.getCardTarget(), true);
            GridPane.setFillHeight(card.getCardTarget(), true);
            GridPane.setMargin(card.getCardTarget(), new Insets(10, 10, 10, 10));
            GridPane.setRowIndex(card.getCardTarget(), wordMap.get(wordCards.get(i).getWord()) / numCols);
            GridPane.setColumnIndex(card.getCardTarget(), wordMap.get(wordCards.get(i).getWord()) % numCols);
            myGridPane.getChildren().add(card.getCardTarget());

            Card card1 = new Card();
            card1.getCardTarget().setFont(Font.font("Charis SIL", FontWeight.BOLD, 16));
            card1.setCardText(wordCards.get(i).getPronunciation());
            GridPane.setFillWidth(card1.getCardTarget(), true);
            GridPane.setFillHeight(card1.getCardTarget(), true);
            GridPane.setMargin(card1.getCardTarget(), new Insets(10, 10, 10, 10));
            GridPane.setRowIndex(card1.getCardTarget(), wordMap.get(wordCards.get(i).getPronunciation()) / numCols);
            GridPane.setColumnIndex(card1.getCardTarget(), wordMap.get(wordCards.get(i).getPronunciation()) % numCols);
            myGridPane.getChildren().add(card1.getCardTarget());

            Card card2 = new Card();
            card2.setCardText(wordCards.get(i).getMeaning());
            GridPane.setFillWidth(card2.getCardTarget(), true);
            GridPane.setFillHeight(card2.getCardTarget(), true);
            GridPane.setMargin(card2.getCardTarget(), new Insets(10, 10, 10, 10));
            GridPane.setRowIndex(card2.getCardTarget(), wordMap.get(wordCards.get(i).getMeaning()) / numCols);
            GridPane.setColumnIndex(card2.getCardTarget(), wordMap.get(wordCards.get(i).getMeaning()) % numCols);
            myGridPane.getChildren().add(card2.getCardTarget());
        }

        gameStarted = true;
        System.out.println("Game started: " + gameStarted);
    }

    public static class Card {
        private Button cardTarget = new Button();
        private String cardText = "";
        private final RotateTransition flipTransition1 = new RotateTransition();
        private final RotateTransition flipTransition2 = new RotateTransition();
        private boolean isFlipped;

        public Card() {
            FXMLManager fxmlManager = new FXMLManager();
            cardTarget.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 16));
            cardTarget.setTextAlignment(TextAlignment.CENTER);
            cardTarget.setMaxWidth(Double.MAX_VALUE);
            cardTarget.setMaxHeight(Double.MAX_VALUE);
            cardTarget.setWrapText(true);
            setUpFlipTransition1();
            setUpFlipTransition2();
            cardTarget.setOnAction(event -> {
                flip();
            });
            isFlipped = false;
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
            isFlipped = !isFlipped;

            String wordText = "";

            if(gameStarted && isFlipped) {
                flippedCards.add(this);
                System.out.println("Flipped cards: " + flippedCards.size());
                if(flippedCards.size() == 3) {
                    for(Card card : flippedCards) {
                        if(card.cardText.contains("(")) {
                            wordText = card.cardText;
                            System.out.println(card.cardText + " contains (");
                        }
                    }
                    if(wordText.equals("")) {
                        System.out.println("No word found");
                        gameStarted = false;
                        for(Card card : flippedCards) {
                            System.out.println("Card flipped back: " + card.cardText);
                            card.normalFlip();
                        }

                        flippedCards.clear();

                        gameStarted = true;
                        return;
                    }
                    for(WordInCard wordInCard : wordCards) {
                        if(wordInCard.getWord().equals(wordText)) {
                            System.out.println("Word in card: " + wordInCard.getWord());
                            if((wordInCard.getPronunciation().equals(flippedCards.get(1).cardText) && wordInCard.getMeaning().equals(flippedCards.get(2).cardText))
                            || (wordInCard.getPronunciation().equals(flippedCards.get(2).cardText) && wordInCard.getMeaning().equals(flippedCards.get(1).cardText))) {
                                flippedCards.get(0).cardTarget.setDisable(true);
                                flippedCards.get(1).cardTarget.setDisable(true);
                                flippedCards.get(2).cardTarget.setDisable(true);
                            } else {
                                gameStarted = false;
                                for(Card card : flippedCards) {
                                    System.out.println("Card flipped back: " + card.cardText);
                                    card.normalFlip();
                                }
                                flippedCards.clear();
                            }
                            break;
                        }
                    }
                }
            }
            gameStarted = true;
        }
        private void normalFlip() {
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
            isFlipped = !isFlipped;
        }
    }
}
