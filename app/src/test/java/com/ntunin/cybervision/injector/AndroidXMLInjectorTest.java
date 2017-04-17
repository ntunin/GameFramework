package com.ntunin.cybervision.injector;

import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.opengl.screen.CVGLERContextEmpty;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Created by nikolay on 03.04.17.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)

public class AndroidXMLInjectorTest {
    ERContext activity;
    Injector injector;
    ObjectFactory factory;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(CVGLERContextEmpty.class);
        if(ERContext.current() == null) {
           ERContext.setCurrent(activity);
        }
        this.injector = Injector.main();
        this.factory = (ObjectFactory) injector.getInstance("Object Factory");
    }

    @Test
    public void injectorTest() {
        assertEquals(Injector.main().getInstance("Object Factory") != null, true);
        assertEquals(factory.get("Int Size") != null, true);
        assertEquals(Injector.main().getInstance("News Factory") != null, true);
    }
}
