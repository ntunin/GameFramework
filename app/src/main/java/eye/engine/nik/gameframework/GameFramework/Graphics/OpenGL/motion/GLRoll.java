package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.motion;

import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Game.Body;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLRoll extends GLTransition {
    @Override
    public void act(Body b) {
        GL10 gl = getGL();
        float roll = b.getRoll();
        gl.glRotatef(roll, 0, 0, 1);
    }
}
