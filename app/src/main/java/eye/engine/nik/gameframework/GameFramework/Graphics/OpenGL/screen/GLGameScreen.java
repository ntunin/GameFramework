package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen;

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

/**
 * Created by mikhaildomrachev on 19/10/2016.
 */
public class GLGameScreen extends GLScreen{
    public GLGameScreen() {
        super();
    }

    @Override
    public void resume() {
        GLGame.current().resume();
    }

    @Override
    public void update(float deltaTime) {
        GLGame.current().update(deltaTime);
    }
    @Override
    public void present(float deltaTime) {
        GLGame.current().present(deltaTime);
    }

    @Override
    public void pause() {
        GLGame.current().pause();
    }

    @Override
    public void dispose() {
        GLGame.current().dispose();
    }
}
