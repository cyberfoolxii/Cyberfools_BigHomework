package Application.java;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

/** StageManager cung cấp các tiện ích thao tác với Stage. */
public class StageManager {
    /** không cho phép sửa. */
    private final Stage stage;
    private final SceneManager sceneManager;

    /** StageManager CÓ một Stage để tiện thao tác nội bộ. */
    public StageManager(Stage stage, SceneManager sceneManager) {
        this.stage = stage;
        this.sceneManager = sceneManager;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public Stage getStage() {
        return stage;
    }

    /** phương thức này nạp Scene có index trong SceneIndex vào Stage. */
    public void setSceneInListToStage(SceneIndex sceneIndex) {
        stage.setScene(sceneManager.getSceneInSceneList(sceneIndex));
    }

    public void switchScene() {

    }

    /** hiện Stage ra cửa sổ giao diện. */
    public void showStage() {
        stage.show();
    }

    public void configure() {
        stage.setTitle("Dictionary Application");
        stage.setFullScreenExitHint("Nhấn ESC để thoát chế độ toàn màn hình");
        //stage.setResizable(false);
        //stage.setWidth(CommonConstants.MAX_SCREEN_WIDTH/1.5);
        //stage.setHeight(CommonConstants.MAX_SCREEN_HEIGHT/1.5);
        stage.setX((CommonConstants.MAX_SCREEN_WIDTH - CommonConstants.MAX_SCREEN_WIDTH/1.5)/2);
        stage.setY((CommonConstants.MAX_SCREEN_HEIGHT - CommonConstants.MAX_SCREEN_HEIGHT/1.5)/2);
        Image appIcon = new Image("file:src/Application/resources/AppIcon/AppIcon.jpg");
        stage.getIcons().add(appIcon);
        //stage.setFullScreen(true);
/*        RootManager rootManager = new RootManager();
        SceneManager sceneManager = new SceneManager();
        primaryStage.setTitle("Dictionary Application");
        primaryStage.setWidth(1366);
        primaryStage.setHeight(768);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("test");
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }
}
