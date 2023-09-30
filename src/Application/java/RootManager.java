package Application.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.util.Objects;

public final class RootManager {
    public Parent getFXMLInsertedRoot(String fxmlFilePath) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFilePath)));
    }
}
