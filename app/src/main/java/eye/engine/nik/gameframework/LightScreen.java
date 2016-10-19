package eye.engine.nik.gameframework;


import android.opengl.GLU;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Game.Body;
import eye.engine.nik.gameframework.GameFramework.Game.Game;
import eye.engine.nik.gameframework.GameFramework.Game.Operator;
import eye.engine.nik.gameframework.GameFramework.Game.Scene;
import eye.engine.nik.gameframework.GameFramework.Game.SceneContext;
import eye.engine.nik.gameframework.GameFramework.Game.World;
import eye.engine.nik.gameframework.GameFramework.Graphics.Frame;
import eye.engine.nik.gameframework.GameFramework.Graphics.Material;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLActor.GLActor;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLScreen;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.motion.GLTransition;
import eye.engine.nik.gameframework.GameFramework.Graphics.Texture;
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
    SceneContext context;

    public LightScreen() {
        super();
        ambientLight = new AmbientLight();
        ambientLight.setColor(0, 0.2f, 0, 1);
        pointLight = new PointLight(3, 3, 0);
        pointLight.setDiffuse(1, 0, 0, 1);
        directionalLight = new DirectionalLight();
        directionalLight.setDiffuse(0, 0, 1, 1);
        directionalLight.setDirection(1, 0, 0);

        context = new SceneContext();
    }
    @Override
    public void resume() {
    }

    @Override
    public void update(float deltaTime) {
        World world = (World) context.get("world");
        world.update(deltaTime);
    }
    @Override
    public void present(float deltaTime) {
        clean();
        setupCamera();
        setupLight();
        drawActors();
    }

    private void clean() {
        GL10 gl = glGraphics.getGL();
        gl.glClearColor(1f, 0.2f, 0.2f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }
    private void setupCamera() {
        Operator operator = (Operator) context.get("operator");
        operator.shootFrom("main camera");
    }
    private void setupLight() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_LIGHTING);
        ambientLight.enable(gl);
        pointLight.enable(gl, GL10.GL_LIGHT0);
        directionalLight.enable(gl, GL10.GL_LIGHT1);

    }
    private void drawActors() {
        Map<String, GLActor> actors = (Map<String, GLActor>) context.get("actors");
        Set<String> actorNames = actors.keySet();
        for(String name: actorNames) {
            GLActor actor = actors.get(name);
            drawActor(actor);
        }
    }

    private void drawActor(GLActor actor) {
        Body b = actor.getBody();
        Frame f = actor.getDress();
        List<GLTransition> transitions = actor.getTransitions();
        for(GLTransition t: transitions) {
            t.act(b);
        }
        f.draw();
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
