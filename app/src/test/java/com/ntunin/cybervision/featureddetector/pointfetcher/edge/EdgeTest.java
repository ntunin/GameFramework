package com.ntunin.cybervision.featureddetector.pointfetcher.edge;

import android.app.Activity;
import android.os.Build;

import com.ntunin.cybervision.BuildConfig;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.errno.ErrCodes;
import com.ntunin.cybervision.crvobjectfactory.CRVObjectFactory;
import com.ntunin.cybervision.activity.StartActivity;
import com.ntunin.cybervision.crvinjector.TestInjector;
import com.ntunin.cybervision.crvinjector.CRVInjector;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.Edge;
import com.ntunin.cybervision.journal.featureddetector.pointfetcher.edge.EdgeRegister;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.assertEquals;

import math.intpoint.Point;
import math.intsize.Size;

/**
 * Created by nikolay on 26.03.17.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class EdgeTest {
    Activity activity;
    CRVInjector injector;
    CRVObjectFactory factory;

    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(StartActivity.class);
        CRVInjector.setMain(new TestInjector());
        this.injector = CRVInjector.main();
        this.factory = (CRVObjectFactory) injector.getInstance("Object Factory");
    }

    @Test
    public void notInitTest() {
        Edge e = (Edge) factory.get("Edge");
        e.push(newPoint(0, 0));
        assertEquals(ERRNO.last(), ErrCodes.NOT_INITIALIZED);
    }

    @Test
    public void onePointTest() {
        Size size = (Size) factory.get("Int Size").init(10, 10);
        EdgeRegister register = (EdgeRegister) factory.get("Edge Register").init(size);
        Edge e = (Edge) factory.get("Edge").init(register);
        Point[] points = new Point[]{
                newPoint(1, 1)
        };
        for(Point p: points) {
            e.push(p);
        }
        Point first = e.getFirst();
        assertEquals(first.x == 1 && first.y == 1, true);
        Point last = e.getLast();
        assertEquals(last.x == 1 && last.y == 1, true);
    }
    @Test
    public void pushTest() {
        Size size = (Size) factory.get("Int Size").init(10, 10);
        EdgeRegister register = (EdgeRegister) factory.get("Edge Register").init(size);
        Edge e = (Edge) factory.get("Edge").init(register);
        Point[] points = new Point[]{
                newPoint(1, 1),
                newPoint(2, 2),
                newPoint(3, 3),
                newPoint(4, 4),
                newPoint(5, 5),
        };
        for(Point p: points) {
            e.push(p);
        }
        Point first = e.getFirst();
        assertEquals(first.x == 1 && first.y == 1, true);
        Point last = e.getLast();
        assertEquals(last.x == 5 && last.y == 5, true);
    }

    @Test
    public void splitTest() {
        Size size = (Size) factory.get("Int Size").init(10, 10);
        EdgeRegister register = (EdgeRegister) factory.get("Edge Register").init(size);
        Edge e = (Edge) factory.get("Edge").init(register);
        Point[] points = new Point[]{
                newPoint(1, 1),
                newPoint(2, 2),
                newPoint(3, 3),
                newPoint(4, 4),
                newPoint(5, 5),
                newPoint(6, 6),
                newPoint(7, 7),
                newPoint(8, 8),
                newPoint(9, 9)
        };
        for(Point p: points) {
            e.push(p);
        }
        Edge e2 = e.split(newPoint(4, 4));
        Point first = e.getFirst();
        Point last = e.getLast();
        assertEquals(first.x == 1 && first.y == 1, true);
        assertEquals(last.x == 4 && last.y == 4, true);

        first = e2.getFirst();
        last = e2.getLast();
        assertEquals(first.x == 4 && first.y == 4, true);
        assertEquals(last.x == 9 && last.y == 9, true);
    }

    @Test
    public void pushEdgeTest() {Size size = (Size) factory.get("Int Size").init(10, 10);
        EdgeRegister register = (EdgeRegister) factory.get("Edge Register").init(size);
        Edge e = (Edge) factory.get("Edge").init(register);
        Point[] points = new Point[]{
                newPoint(1, 1),
                newPoint(2, 2),
                newPoint(3, 3),
                newPoint(4, 4)
        };
        for(Point p: points) {
            e.push(p);
        }

        Edge e2 = (Edge) factory.get("Edge").init(register);
        Point[] points2 = new Point[]{
                newPoint(5, 5),
                newPoint(6, 6),
                newPoint(7, 7),
                newPoint(8, 8),
                newPoint(9, 9)
        };
        for(Point p: points2) {
            e2.push(p);
        }

        e.push(e2);

        Point first = e.getFirst();
        Point last = e.getLast();
        assertEquals(first.x == 1 && first.y == 1, true);
        assertEquals(last.x == 9 && last.y == 9, true);
    }



    private Point newPoint(int x, int y) {
        return (Point) factory.get("Int Point").init(x, y);
    }
}
