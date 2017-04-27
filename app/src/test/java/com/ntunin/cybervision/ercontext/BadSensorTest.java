package com.ntunin.cybervision.ercontext;

import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.errno.ERRNO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

/**
 * Created by nik on 27.04.17.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class BadSensorTest {
    ERContext activity;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(BadSensorTestActivity.class);
    }

    @Test
    public void test() {
        activity.start();
        assertTrue("Should not have sensor", ERRNO.isLast(R.string.no_motion_sensor));
    }


}