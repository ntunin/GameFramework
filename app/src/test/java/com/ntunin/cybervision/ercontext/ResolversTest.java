package com.ntunin.cybervision.ercontext;

import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.injector.MapInjector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nik on 25.04.17.
 */


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class ResolversTest  {
    ERContext activity;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(NoResolvedActivity.class);
    }

    @Test
    public void noResolverTest() {
        Injector.main().setInstance("NoResolvedService", new NoResolverService() );
        activity.start();

    }

    private class NoResolverService implements Service {

        public void serve() {
            final boolean[] handled = {false};
            ERContext.current().grantRequest(R.string.camera_permission, new GrantListener() {
                @Override
                public void onPermissionGrantedResult(boolean result) {
                    handled[0] = true;
                    assertTrue("Last error should be has no resolver", ERRNO.isLast(R.string.no_grant_resolver));
                }
            });
            assertTrue("Should be handled", handled[0]);
        }

    }

}



