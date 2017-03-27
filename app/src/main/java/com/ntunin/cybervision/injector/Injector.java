package com.ntunin.cybervision.injector;

/**
 * Created by nikolay on 26.01.17.
 */

public abstract class Injector {
    protected static Injector injector;
    public abstract Object getInstance(String token);
    public abstract void setInstance(String token, Object instance);
    public static void setMain(Injector main) {
        injector = main;
    }
    public static Injector main() {
        return InternalInjector.main();
    }
}
