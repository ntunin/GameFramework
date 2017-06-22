package com.ntunin.cybervision.journal.cameracapturing;

import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.crvcontext.CRVContext;
import com.ntunin.cybervision.crvinjector.TestInjector;
import com.ntunin.cybervision.crvinjector.CRVInjector;
import com.ntunin.cybervision.io.ClassLoaderIO;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.crvview.screen.CVGLERContextEmpty;

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
        if(CRVContext.current() == null) {
            CRVContext.setCurrent(activity);
        }
        CRVInjector injector = new TestInjector();
        CRVInjector.setMain(injector);
        injector.setInstance("File", new ClassLoaderIO());
    }


    @Test
    public void loadFileTest() {

        Journal journal = (Journal) CRVInjector.main().getInstance("Journal");
        journal.subscribe("Camera", new JournalSubscriber() {
            @Override
            public void breakingNews(BreakingNews news) {
                ImageFrame frame = (ImageFrame) news.read("Image Frame");
                assert frame != null;
                int[] c = frame.get(200, 150);
                assert true;
            }
        });
        FileCapturing capturing = ((FileCapturing) CRVInjector.main().getInstance("Capturing"));
        capturing.start();
    }
}
