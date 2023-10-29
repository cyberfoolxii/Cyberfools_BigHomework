package Application.java;

import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;

public final class SceneManager {
    /** không cho phép sửa từ bên ngoài.
     * RootManager sử dụng nội bộ để hỗ trợ thao tác cần đến nốt gốc (root)
     */
    private final FXMLManager rootManager = new FXMLManager();
    private final ArrayList<Scene> sceneList = new ArrayList<>();

    /** lấy ra Scene có index tương ứng, xem tại enum SceneIndex. */
    public Scene getSceneInSceneList(SceneIndex index) {
        if (index.ordinal() < sceneList.size()) {
            return sceneList.get(index.ordinal());
        }
        System.out.println("the scene at index " + index.ordinal() + " is not existed");
        return null;
    }

    /** phương thức tạo Scene mới sử dụng nội bộ hỗ trợ cho initializeSceneList(). */
    private Scene createNewScene(String path) {
        Scene newScene = null;
        try{
            newScene = new Scene(rootManager.getFXMLInsertedRoot(path));
        }
        catch (IOException e) {
            System.out.println("couldn't create a scene");
            e.printStackTrace();
        }
        return newScene;
    }

    /** khởi tạo danh sách Scene, mỗi khi thêm một tệp fxml mới thì hãy copy thêm một
     * dòng lệnh add và thay đường dẫn bằng đường dẫn tệp fxml mới.
     */
    public void initializeScenes() {
        //sceneList.add(createNewScene("/FXML Files/DictionaryApplicationScene.fxml"));
        sceneList.add(createNewScene("/FXML Files/FXML.fxml"));
    }
}
