package com.ntunin.cybervision.injector;

import android.app.Activity;
import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.CVGLTest;
import com.ntunin.cybervision.ERRNO;
import com.ntunin.cybervision.ErrCodes;
import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.StartActivity;
import com.ntunin.cybervision.TestInjector;
import com.ntunin.cybervision.game.Game;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.Edge;
import com.ntunin.cybervision.opengl.screen.CVGLGameEmpty;
import com.ntunin.cybervision.opengl.screen.GLGame;

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
    Game activity;
    Injector injector;
    ObjectFactory factory;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(CVGLGameEmpty.class);
        if(Game.current() == null) {
           Game.setCurrent(activity);
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
