package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.camera;


import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.CVGLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.GLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.GLGraphics;
import eye.engine.nik.gameframework.GameFramework.Vector3;

/**
 * Created by nikolay on 12.10.16.
 */

public class PerspectiveCamera implements Camera {
    private PerspectiveRacurs racurs;
    public PerspectiveCamera(PerspectiveRacurs racurs) {
        this.racurs = racurs;
    }
    @Override
    public void motor() {
        CVGLGame game = CVGLGame.current();
        GLGraphics glGraphics = game.getGLGraphics();
        int height = glGraphics.getHeight();
        int width = glGraphics.getWidth();
        GL10 gl = glGraphics.getGL();


        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, racurs.angle, 1f*width / height, racurs.near, racurs.far);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        Vector3 position = racurs.position;
        Vector3 target = racurs.target;
        Vector3 up = racurs.up;

        GLU.gluLookAt(gl, position.x, position.y, position.z, target.x, target.y, target.z, up.x, up.y, up.z);
    }
}
