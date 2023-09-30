package Application.java;

import javafx.scene.Scene;
import java.util.ArrayList;

public final class SceneManager {
    private final ArrayList<Scene> sceneList = new ArrayList<>();
    public Scene getSceneInSceneList(SceneIndex index) {
        if (index.ordinal() < sceneList.size()) {
            return sceneList.get(index.ordinal());
        }
        System.out.println("the scene at index " + index.ordinal() + " is not existed");
        return null;
    }
    public void setSceneToList(Scene scene) {
        if (scene != null) {
            sceneList.add(scene);
            return;
        }
        System.out.println("the added Scene object is null");
    }
}
