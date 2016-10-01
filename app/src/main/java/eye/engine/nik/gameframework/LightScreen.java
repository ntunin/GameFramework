package eye.engine.nik.gameframework;


import android.opengl.GLU;
import android.provider.Settings;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Game.Game;
import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLGraphics;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLScreen;
import eye.engine.nik.gameframework.GameFramework.Graphics.Scene;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XFile;

/**
 * Created by nik on 07.04.16.
 */
public class LightScreen extends GLScreen {
    float angle;
    Scene scene;
    private GL10 gl;
    GLGraphics glGraphics;
    Frame f;

    public LightScreen(Game game) {
        super(game);
        glGraphics = ((GLGame) game).getGLGraphics();
        try {
            f = XFile.loadFrame("animation.X");
        } catch (GameIOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void resume() {
    }

    @Override
    public void update(float deltaTime) {
        angle += deltaTime * 20;
    }
    @Override
    public void present(float deltaTime) {
        setupCamera();
        draw();
    }
    private void setupCamera() {
        gl = glGraphics.getGL();
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight()); gl.glClear(GL10.GL_COLOR_BUFFER_BIT); gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, 20, 0, 80, 20, -20);
    }
    private void draw() {
        if( f!= null)
            f.draw();
    }
    private void drawElements() {
        scene.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }

}
