package com.ntunin.cybervision.journal.cameracapturing;

import android.app.Activity;
import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.StartActivity;
import com.ntunin.cybervision.TestInjector;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


/**
 * Created by nikolay on 12.03.17.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class FileCapturingTest {

    Activity activity;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(StartActivity.class);
    }


    @Test
    public void loadFileTest() {
        Injector injector = new TestInjector();
        Injector.setMain(injector);

        Journal journal = (Journal) Injector.main().getInstance("Journal");
        journal.subscribe("Image Frame", new JournalSubscriber() {
            @Override
            public void breakingNews(BreakingNews news) {
                ImageFrame frame = (ImageFrame) news.read("Image Frame");
                assert frame != null;
                assert frame.getBrightness(0, 0) == 255;
                assert frame.getBrightness(8, 8) == 0;
            }
        });
        FileCapturing capturing = ((FileCapturing) Injector.main().getInstance("Capturing")).init("9_1.f");
        capturing.start();
    }
}