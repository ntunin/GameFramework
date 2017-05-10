package com.ntunin.cybervision.android.io;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ntunin.cybervision.ercontext.ERContext;

/**
 * Created by nik on 27.04.17.
 */

public abstract class ContextSensor implements SensorEventListener {


    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {

    }

    protected static Sensor getHardwareSensor(int type) {
        SensorManager manager = (SensorManager) ERContext.current()
                .getSystemService(Context.SENSOR_SERVICE);
        android.hardware.Sensor sensor = manager.getDefaultSensor(type);
        return sensor;
    }

    public static void startSensorTracking(ContextSensor handler, int type) {
        SensorManager manager = (SensorManager) ERContext.current()
                .getSystemService(Context.SENSOR_SERVICE);
        android.hardware.Sensor sensor = manager.getDefaultSensor(type);
        manager.registerListener(handler, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
