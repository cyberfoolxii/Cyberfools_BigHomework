package Application.java;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        StageManager stageManager = new StageManager(primaryStage, SceneManager.getInstance());
        SceneManager.getInstance().initializeScenes();
        stageManager.configure();
        LocalDictionaryManager.getInstance().insertWordFromFile();
        stageManager.setSceneInListToStage(SceneIndex.HOMEINDEX);
        stageManager.showStage();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
        });
    }
    public void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát");
        alert.setHeaderText("Bạn sắp thoát ứng dụng!");
        alert.setContentText("Lưu trạng thái từ điển trước khi thoát?: ");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("exit");
            LocalDictionaryManager.getInstance().exportWordToFile();
            stage.close();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
