package Application.java;

import javafx.application.Application;
import javafx.stage.Stage;

public class DictionaryApplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = new SceneManager();
        StageManager stageManager = new StageManager(primaryStage, sceneManager);
        sceneManager.initializeScenes();
        stageManager.configure();
        stageManager.setSceneInListToStage(SceneIndex.HOMEINDEX);
        DictionaryManagement dictionaryManagement = new DictionaryManagement(Dictionary.getInstance());
        dictionaryManagement.insertWordFromFile();
        stageManager.showStage();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
