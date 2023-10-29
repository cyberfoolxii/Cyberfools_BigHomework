package Application.java;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.media.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
public class DictionaryApplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = new SceneManager();
        StageManager stageManager = new StageManager(primaryStage, sceneManager);
        sceneManager.initializeScenes();
        stageManager.configure();
        stageManager.setSceneInListToStage(SceneIndex.HOMEINDEX);
        Dictionary dictionary = new Dictionary();
        stageManager.showStage();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
