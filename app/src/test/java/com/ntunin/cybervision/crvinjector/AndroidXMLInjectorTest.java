package com.ntunin.cybervision.crvinjector;

import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.crvobjectfactory.CRVObjectFactory;
import com.ntunin.cybervision.crvcontext.CRVContext;
import com.ntunin.cybervision.crvview.screen.CVGLERContextEmpty;

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
    CRVContext activity;
    CRVInjector injector;
    CRVObjectFactory factory;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(CVGLERContextEmpty.class);
        if(CRVContext.current() == null) {
           CRVContext.setCurrent(activity);
        }
        this.injector = CRVInjector.main();
        this.factory = (CRVObjectFactory) injector.getInstance("Object Factory");
    }

    @Test
    public void injectorTest() {
        assertEquals(CRVInjector.main().getInstance("Object Factory") != null, true);
        assertEquals(factory.get("Int Size") != null, true);
        assertEquals(CRVInjector.main().getInstance("News Factory") != null, true);
    }
}
