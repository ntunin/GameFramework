package eye.engine.nik.gameframework.GameFramework.Light;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by nick on 01.03.16.
 */
public class DirectionalLight extends Light {
    float[] direction = { 0, 0, -1, 0 };
    public void enable(GL10 gl, int lightId) {
        gl.glLightfv(lightId, GL10.GL_POSITION, direction, 0);
        lastLightId = lightId;
    }
    public void setDirection(float x, float y, float z) {
        direction[0] = -x;
        direction[1] = -y;
        direction[2] = -z;
    }
}
