package Application.java;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.FontWeight;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLManager fxmlManager = new FXMLManager();
        questionLabel.setFont(fxmlManager.cloneQuicksandFont(FontWeight.BOLD, 18));
        answerButton1.fontProperty().bind(questionLabel.fontProperty());
        answerButton2.fontProperty().bind(questionLabel.fontProperty());
        answerButton3.fontProperty().bind(questionLabel.fontProperty());
        answerButton4.fontProperty().bind(questionLabel.fontProperty());
        initTimerMediaView();
    }

    private void initTimerMediaView() {
        Media media = new Media(new File("src\\Application\\resources\\Animation\\Countdown.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        timerMediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }
}