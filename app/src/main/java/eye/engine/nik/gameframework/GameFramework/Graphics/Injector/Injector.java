package eye.engine.nik.gameframework.GameFramework.Graphics.Injector;

/**
 * Created by nikolay on 26.01.17.
 */

public abstract class Injector {
    public abstract Object getInstance(String token);
    public abstract void setInstance(String token, Object instance);
    public static Injector main() {
        return InternalInjector.main();
    }
}
