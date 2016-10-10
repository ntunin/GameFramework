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
import eye.engine.nik.gameframework.GameFramework.Graphics.Material;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLGame;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLGraphics;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLScreen;
import eye.engine.nik.gameframework.GameFramework.Graphics.Scene;
import eye.engine.nik.gameframework.GameFramework.Graphics.Texture;
import eye.engine.nik.gameframework.GameFramework.IO.GameIOException;
import eye.engine.nik.gameframework.GameFramework.IO.XFile.XFile;
import eye.engine.nik.gameframework.GameFramework.Light.AmbientLight;
import eye.engine.nik.gameframework.GameFramework.Light.DirectionalLight;
import eye.engine.nik.gameframework.GameFramework.Light.PointLight;

/**
 * Created by nik on 07.04.16.
 */
public class LightScreen extends GLScreen {
    float angle;
    AmbientLight ambientLight;
    PointLight pointLight;
    DirectionalLight directionalLight;
    Material material;
    Frame f;
    Texture t;

    public LightScreen(Game game) {
        super(game);
        ambientLight = new AmbientLight();
        ambientLight.setColor(0, 0.2f, 0, 1);
        pointLight = new PointLight(3, 3, 0);
        pointLight.setDiffuse(1, 0, 0, 1);
        directionalLight = new DirectionalLight();
        directionalLight.setDiffuse(0, 0, 1, 1);
        directionalLight.setDirection(1, 0, 0);
        material = new Material();

        try {
            f = XFile.loadFrame("cedr.X");
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
        GL10 gl = glGraphics.getGL();
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 67, glGraphics.getWidth()
                / (float) glGraphics.getHeight(), 0.1f, 1000f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, -10f, 0, 0, 0, 0, 1, 0);
        gl.glEnable(GL10.GL_LIGHTING);
        ambientLight.enable(gl);
        pointLight.enable(gl, GL10.GL_LIGHT0);
        directionalLight.enable(gl, GL10.GL_LIGHT1);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glRotatef(angle, 0, 1, 0);
        draw();
        pointLight.disable(gl);
        directionalLight.disable(gl);
        gl.glDisable(GL10.GL_TEXTURE_2D);
        gl.glDisable(GL10.GL_DEPTH_TEST);
    }
    private void setupCamera() {
    }
    private void draw() {
        if( f!= null)
            f.draw();
    }
    private void drawElements() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }

}
