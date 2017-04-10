package com.ntunin.cybervision.journal.camerapositioneer;

import android.hardware.SensorManager;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ResMap;
import math.vector.Vector3;
import com.ntunin.cybervision.android.io.AccelerometerHandler;
import com.ntunin.cybervision.android.io.CompassHandler;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.journal.JournalSubscriber;

/**
 * Created by nikolay on 01.02.17.
 */

public class JournalingCameraTransformLocator implements JournalSubscriber, Injectable {

    private Journal journal;
    private AccelerometerHandler accelerometer;
    private CompassHandler compass;
    private long time;
    private Vector3 v;
    private Vector3 x;
    private String tag;
    private ObjectFactory factory;

    private float[] rotationMatrix = new float[16];
    private float[] accelData = new float[3];
    private float[] orientationData = new float[3];
    private float[] magnetData = new float[3];

    public JournalingCameraTransformLocator() {
    }


    @Override
    public void breakingNews(BreakingNews news) {

        if(v == null) {
            time = getTime();
            v = (Vector3) factory.get(R.string.vector3).init();
        }

        Vector3 a = accelerometer.getAcceleration();
        Vector3 c = compass.getMagnetData();

        getArrrayFromVector(accelData, a);
        getArrrayFromVector(magnetData, c);


        SensorManager.getRotationMatrix(rotationMatrix, null, accelData, orientationData);
        SensorManager.getOrientation(rotationMatrix, orientationData);

        float t = resetTime();
        Vector3 v = this.v.cpy().add(a.mul(t));
        Vector3 x = v.cpy().mul(t).add(a.mul(sqr(t)/2));

        this.v = v;
        this.x = x;

        news.write(R.string.position, x);
        news.write(R.string.speed, v);
        //news.write(R.string.rotation, rotationMatrix);
        //news.write(R.string.orientation, orientationData);

        journal.release(tag, news);
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

    @Override
    public void init(ResMap<String, Object> data) {
        accelerometer = (AccelerometerHandler) data.get(R.string.accelerometer);
        compass = (CompassHandler) data.get(R.string.compass);
        journal = (Journal) data.get(R.string.journal);
        String action = (String) data.get(R.string.camera_action);
        tag = (String) data.get(R.string.position_action);
        factory = (ObjectFactory) data.get(R.string.object_factory);
        journal.subscribe(action, this);

    }
}
