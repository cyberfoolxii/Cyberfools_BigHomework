package Application.java;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class SceneController {
    public void switchScene(MouseEvent event) throws IOException {
        RootManager rootManager = new RootManager();
        Parent root = rootManager.getFXMLInsertedRoot("DictionaryApplication.fxml");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); /** get the source node that generated this event,
         since every node class inherits Node, this typecasting is valid, from this node, get the corresponding scene then from
         the scene, we get the window*/
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
