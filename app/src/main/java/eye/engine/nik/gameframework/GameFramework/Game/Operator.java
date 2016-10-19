package eye.engine.nik.gameframework.GameFramework.Game;

import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.camera.Camera;

/**
 * Created by nikolay on 12.10.16.
 */

public class Operator {
    Map<String, Camera> cameras;
    public Operator(Map<String, Camera> cameras) {
        this.cameras = cameras;
    }
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
