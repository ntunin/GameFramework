package com.ntunin.cybervision.crvcontext;

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
 * Created by nik on 26.04.17.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class BadImageFrameTest {
    CRVContext activity;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(BadFrameViewTestActivity.class);
    }

    @Test
    public void noCameraResolverTest() {
        activity.start();
        assertTrue("Should not grant camera permission", ERRNO.isLast(R.string.camera_not_granted));
    }


}