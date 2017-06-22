package com.ntunin.cybervision.featureddetector.divider.ninepointsdivider;

import android.app.Activity;
import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.crvobjectfactory.CRVObjectFactory;
import com.ntunin.cybervision.activity.StartActivity;
import com.ntunin.cybervision.crvinjector.TestInjector;
import com.ntunin.cybervision.crvinjector.CRVInjector;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.FileCapturing;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.featureddetector.divider.DividerDelegate;
import com.ntunin.cybervision.journal.featureddetector.divider.ninepointsdivider.NinePointsDivider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.LinkedList;
import java.util.List;

import math.intpoint.Point;
import math.intsize.Size;

/**
 * Created by nikolay on 12.03.17.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class NinePointsDividerTest {

    Activity activity;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(StartActivity.class);
    }

    @Test
    public void test9_1() {
        test("9_1.f", 5, 2, 6, new PointListInspector() {
            @Override
            void check(List<Point> points) {
                assert points != null;
                assert points.size() == 6;
            }
        });
    }



    @Test
    public void test9_1_2() {
        test("9_1.f", 7, 6, 2, new PointListInspector() {
            @Override
            void check(List<Point> points) {
                assert points != null;
                assert points.size() == 9;
            }
        });
    }

    private void test(String src, final int x, final int y, final int direction, final PointListInspector inspector) {
        CRVInjector injector = new TestInjector();
        CRVInjector.setMain(injector);

        Journal journal = (Journal) CRVInjector.main().getInstance("Journal");
        FileCapturing capturing = ((FileCapturing) CRVInjector.main().getInstance("Capturing"));
        journal.subscribe("Image Frame", new JournalSubscriber() {
            @Override
            public void breakingNews(BreakingNews news) {
                check(news, x, y, direction, inspector);
            }
        });
        capturing.start();
    }

    private void check(BreakingNews news, int x, int y, final int direction, PointListInspector inspector) {
        final ImageFrame frame = (ImageFrame) news.read("Image Frame");
        assert frame != null;
        final CRVObjectFactory factory = (CRVObjectFactory) CRVInjector.main().getInstance("Object Factory");
        final List<Point> points = new LinkedList<Point>();
        final Size s = frame.size();
        NinePointsDivider divider = (NinePointsDivider) factory.get("Nine Points").init(frame, new DividerDelegate() {
            @Override
            public boolean addPoint(int x, int y) {
                if(x == 0 || x == s.width - 1 || y == 0 || y == s.height - 1) {
                    return false;
                }
                points.add((Point) factory.get("Int Point").init(x, y));
                return true;
            }

            @Override
            public int getDirection() {
                return direction;
            }
        });
        divider.handle(x, y);
        inspector.check(points);
    }


    private abstract class PointListInspector {
        abstract void check(List<Point> points);
    }

}
