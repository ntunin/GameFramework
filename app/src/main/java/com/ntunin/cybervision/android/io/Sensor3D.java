package com.ntunin.cybervision.android.io;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ntunin.cybervision.ercontext.ERContext;

/**
 * Created by nik on 27.04.17.
 */

public abstract class Sensor3D implements SensorEventListener {
    protected void register(int type) {
        SensorManager manager = (SensorManager) ERContext.current()
                .getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(android.hardware.Sensor.TYPE_GYROSCOPE).size() != 0) {
            android.hardware.Sensor sensor = manager.getSensorList(type).get(0);
            manager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {

    }
}
