package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.motion;

import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Game.Body;
import eye.engine.nik.gameframework.GameFramework.Vector3;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLTranslation extends GLTransition {

    @Override
    public void act(Body b) {
        GL10 gl = getGL();
        Vector3 r = b.getR();
        gl.glTranslatef(r.x, r.y, r.z);
    }
}
