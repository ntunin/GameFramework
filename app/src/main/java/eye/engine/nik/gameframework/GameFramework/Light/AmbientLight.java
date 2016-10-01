package eye.engine.nik.gameframework.GameFramework.Light;
import javax.microedition.khronos.opengles.GL10;
/**
 * Created by nick on 01.03.16.
 */
public class AmbientLight {
    float[] color = {1, 1, 1, 1};
    public AmbientLight(float r, float g, float b, float a) {
        setColor(r, g, b, a);
    }
    public AmbientLight() {

    }
    public void setColor(float r, float g, float b, float a) {
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;
    }
    public  void enable(GL10 gl) {
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, color, 0);
    }
}
