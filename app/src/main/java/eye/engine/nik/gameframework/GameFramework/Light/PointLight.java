package eye.engine.nik.gameframework.GameFramework.Light;
import javax.microedition.khronos.opengles.GL10;
/**
 * Created by nick on 01.03.16.
 */
public class PointLight extends Light{
    float[] position = { 0, 0, 0, 1 };
    public void enable(GL10 gl, int lightId) {
        super.enable(gl, lightId);
        gl.glLightfv(lightId, GL10.GL_POSITION, position, 0);
        lastLightId = lightId;
    }
    public  PointLight(float x, float y, float z) {
        setPosition(x, y, z);
    }
    public void setPosition(float x, float y, float z) {
        position[0] = x;
        position[1] = y;
        position[2] = z;
    }

}
