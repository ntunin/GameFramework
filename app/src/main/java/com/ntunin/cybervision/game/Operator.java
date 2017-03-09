package com.ntunin.cybervision.game;

import com.ntunin.cybervision.opengl.camera.Camera;

import java.util.Map;


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
