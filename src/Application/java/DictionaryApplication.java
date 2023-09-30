package Application.java;

import java.io.File;
import java.util.Collections;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.Event;
public class DictionaryApplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        RootManager rootManager = new RootManager();
        SceneManager sceneManager = new SceneManager();
        sceneManager.setSceneToList(new Scene(rootManager.getFXMLInsertedRoot("DictionaryApplicationScene.fxml")));
        primaryStage.setTitle("Dictionary Application");
        primaryStage.setWidth(1366);
        primaryStage.setHeight(768);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("test");
        Image appIcon = new Image("file:resources/AppIcon.jpg");
        primaryStage.setScene(sceneManager.getSceneInSceneList(SceneIndex.FIRST));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
