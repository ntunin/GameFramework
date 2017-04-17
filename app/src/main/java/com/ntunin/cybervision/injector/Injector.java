package com.ntunin.cybervision.injector;

import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.res.Res;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by nikolay on 26.01.17.
 */

public abstract class Injector {
    protected static Injector injector;
    public abstract Object getInstance(String token);
    public abstract Object getInstance(int id);
    public abstract void setInstance(String token, Object instance);
    public abstract void setInstance(int id, Object instance);
    public static void setMain(Injector main) {
        injector = main;
    }
    public static Injector main() {
        if(injector == null) {
            injector = loadInjector();
        }
        return injector;
    }

    protected static Injector loadInjector() {
        String className = Res.string(R.string.injector);
        try {
            Class<Injector> injectorClass = (Class<Injector>) Class.forName(className);
            Constructor<Injector> constructor = injectorClass.getConstructor();
            return constructor.newInstance();
        } catch (ClassNotFoundException e) {
            ERRNO.write(R.string.class_not_fount);
        } catch (NoSuchMethodException e) {
            ERRNO.write(R.string.constructor_not_fount);
        } catch (IllegalAccessException e) {
            ERRNO.write(R.string.illegal_access);
        } catch (InstantiationException e) {
            ERRNO.write(R.string.could_not_create);
        } catch (InvocationTargetException e) {
            ERRNO.write(R.string.could_not_invoke);
        }
        return null;
    }
}
