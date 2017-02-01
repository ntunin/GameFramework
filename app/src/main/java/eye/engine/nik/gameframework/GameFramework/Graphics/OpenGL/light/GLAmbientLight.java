package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.light;
import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.CVGLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.GLGame;

/**
 * Created by nick on 01.03.16.
 */
public class GLAmbientLight extends GLLight{
    float[] color = {1, 1, 1, 1};

    public GLAmbientLight(int id) {
        super(id);
    }

    public GLAmbientLight() {
        super();
    }

    public void setColor(float r, float g, float b, float a) {
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;
    }
    public  void enable() {
        GL10 gl = CVGLGame.current().getGLGraphics().getGL();
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, color, 0);
    }
}
