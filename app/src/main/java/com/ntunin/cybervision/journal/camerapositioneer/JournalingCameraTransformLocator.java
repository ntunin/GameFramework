package com.ntunin.cybervision.journal.camerapositioneer;

import android.hardware.SensorManager;

import com.ntunin.cybervision.Vector3;
import com.ntunin.cybervision.android.io.AccelerometerHandler;
import com.ntunin.cybervision.android.io.CompassHandler;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;

/**
 * Created by nikolay on 01.02.17.
 */

public class JournalingCameraTransformLocator implements JournalSubscriber {

    private Journal journal;
    private Injector injector;
    private AccelerometerHandler accelerometer;
    private CompassHandler compass;
    private long time;
    private Vector3 v;
    private Vector3 x;

    private float[] rotationMatrix = new float[16];
    private float[] accelData = new float[3];
    private float[] orientationData = new float[3];
    private float[] magnetData = new float[3];

    public JournalingCameraTransformLocator() {
    }

    public void start() {
        injector = Injector.main();
        accelerometer = (AccelerometerHandler) injector.getInstance("Accelerometer");
        compass = (CompassHandler) injector.getInstance("Compass");
        journal = (Journal) injector.getInstance("Journal");
        time = getTime();
        v = new Vector3();
        journal.subscribe("Camera", this);
    }

    public void stop() {

    }

    @Override
    public void breakingNews(BreakingNews news) {

        Vector3 a = accelerometer.getAcceleration();
        Vector3 c = compass.getMagnetData();

        getArrrayFromVector(accelData, a);
        getArrrayFromVector(magnetData, c);


        SensorManager.getRotationMatrix(rotationMatrix, null, accelData, orientationData);
        SensorManager.getOrientation(rotationMatrix, orientationData);

        float t = resetTime();
        Vector3 v = this.v.add(a.mul(t));
        Vector3 x = this.v.mul(t).add(a.mul(sqr(t)/2));

        this.v = v;
        this.x = x;

        news.write("Position", x);
        news.write("Speed", v);
        news.write("Rotation", rotationMatrix);
        news.write("Orientation", orientationData);

        journal.release("Position", news);
    }

    private void getArrrayFromVector(float[] a, Vector3 v) {
        a[0] = v.x;
        a[1] = v.y;
        a[2] = v.z;
    }

    private float resetTime() {
        long newTime = getTime();
        long interval = newTime - time;
        time = newTime;
        return interval / 1000f;
    }

    private float sqr(float a) {
        return a * a;
    }
    private long getTime() {
        return System.currentTimeMillis();
    }
}
