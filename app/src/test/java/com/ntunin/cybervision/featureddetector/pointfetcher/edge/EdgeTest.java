package com.ntunin.cybervision.featureddetector.pointfetcher.edge;

import android.app.Activity;
import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.ERRNO;
import com.ntunin.cybervision.ErrCodes;
import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.StartActivity;
import com.ntunin.cybervision.TestInjector;
import com.ntunin.cybervision.featureddetector.divider.ninepointsdivider.NinePointsDividerTest;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.Edge;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import math.intpoint.Point;

/**
 * Created by nikolay on 26.03.17.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class EdgeTest {
    Activity activity;
    Injector injector;
    ObjectFactory factory;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(StartActivity.class);
        Injector.setMain(new TestInjector());
        this.injector = Injector.main();
        this.factory = (ObjectFactory) injector.getInstance("Object Factory");
    }

    @Test
    public void notInitTest() {
        assert false;
    }

    private Edge newEdge(Point[] points) {
        Edge e = (Edge) factory.get("Edge").init();
        for(int i = 0; i < points.length; i++) {
            e.add(points[i]);
        }
        return e;
    }

    private Point newPoint(int x, int y) {
        return (Point) factory.get("Int Point").init(x, y);
    }
}
