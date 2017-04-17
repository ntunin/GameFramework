package com.ntunin.cybervision.journal.cameracapturing;

import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.injector.TestInjector;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.io.ClassLoaderIO;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.opengl.screen.CVGLERContextEmpty;

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

    CVGLERContextEmpty activity;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(CVGLERContextEmpty.class);
        if(ERContext.current() == null) {
            ERContext.setCurrent(activity);
        }
        Injector injector = new TestInjector();
        Injector.setMain(injector);
        injector.setInstance("File", new ClassLoaderIO());
    }


    @Test
    public void loadFileTest() {

        Journal journal = (Journal) Injector.main().getInstance("Journal");
        journal.subscribe("Camera", new JournalSubscriber() {
            @Override
            public void breakingNews(BreakingNews news) {
                ImageFrame frame = (ImageFrame) news.read("Image Frame");
                assert frame != null;
                int[] c = frame.get(200, 150);
                assert true;
            }
        });
        FileCapturing capturing = ((FileCapturing) Injector.main().getInstance("Capturing"));
        capturing.start();
    }
}
