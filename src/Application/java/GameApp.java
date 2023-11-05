package Application.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML Files/Game.fxml"));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/FXML Files/GameStyles.css").toExternalForm());

        primaryStage.setTitle("Vocabulary Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
