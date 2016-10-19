package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.light;
import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.GLGame;

/**
 * Created by nick on 01.03.16.
 */
public class GLPointLight extends GLLight {
    float[] position = { 0, 0, 0, 1 };

    public GLPointLight(int id) {
        super(id);
    }

    public void enable() {
        super.enable();
        GL10 gl = GLGame.current().getGLGraphics().getGL();
        gl.glLightfv(lightId, GL10.GL_POSITION, position, 0);
        lastLightId = lightId;
    }
    public void setPosition(float x, float y, float z) {
        position[0] = x;
        position[1] = y;
        position[2] = z;
    }

}
