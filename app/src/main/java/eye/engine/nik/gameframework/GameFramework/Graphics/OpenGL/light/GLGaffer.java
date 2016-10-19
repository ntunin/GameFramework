package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.light;

import java.util.Map;

/**
 * Created by mikhaildomrachev on 19/10/2016.
 */
public class GLGaffer {
    private Map<String, GLLight> lights;

    public GLGaffer(Map<String, GLLight> lights) {
        this.lights = lights;
    }

    public void enable(String lightName) {
        GLLight light = lights.get(lightName);
        light.enable();
    }


}
