package eye.engine.nik.gameframework;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import eye.engine.nik.gameframework.GameFramework.Game.Body;
import eye.engine.nik.gameframework.GameFramework.Game.Operator;
import eye.engine.nik.gameframework.GameFramework.Game.SceneContext;
import eye.engine.nik.gameframework.GameFramework.Game.World;
import eye.engine.nik.gameframework.GameFramework.Graphics.GLDress;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.actor.GLActor;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.light.GLGaffer;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.motion.GLTransition;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen.GLScreenedGame;

/**
 * Created by mikhaildomrachev on 19/10/2016.
 */
public class ScreenedGameTest extends GLScreenedGame {
    SceneContext context;
    public ScreenedGameTest() {
        super();
        context = new SceneContext();
        setupCamera();
        setupLight();
    }

    private void setupCamera() {
        Operator operator = (Operator) context.get("operator");
        operator.shootFrom("main camera");
    }

    private void setupLight() {
        GLGaffer gaffer = (GLGaffer) context.get("gaffer");
        gaffer.enable("lights.ambient-1");
        gaffer.enable("lights.point-1");
        gaffer.enable("lights.directional-1");
    }

    @Override
    public void update(float deltaTime) {
        World world = (World) context.get("world");
        world.update(deltaTime);
    }
    @Override
    public void present(float deltaTime) {
        clean(0, 0, 0, 0);
        drawActors();
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
        GLDress f = actor.getDress();
        List<GLTransition> transitions = actor.getTransitions();
        for(GLTransition t: transitions) {
            t.act(b);
        }
        f.draw();
    }
}
