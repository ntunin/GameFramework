package eye.engine.nik.gameframework.GameFramework.Game;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikolay on 12.10.16.
 */

public class Operator {
    Map<String, Camera> cameras = new HashMap<>();

    public void take(Camera camera, String cameraName) {
        cameras.put(cameraName, camera);
    }
    public void take(String cameraName) {
    }
    public void shootFrom(String cameraName) {
        Camera camera = cameras.get(cameraName);
        camera.motor();
    }
}
