package eye.engine.nik.gameframework.GameFramework.Game;

/**
 * Created by nikolay on 12.10.16.
 */

public class Scene {
    private Operator operator = new Operator();
    private Decorations decorations;

    public Scene() {
        operator.take("main camera");
        SceneContext c = new SceneContext();
    }
}
