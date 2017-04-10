package com.ntunin.cybervision.android.io;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ntunin.cybervision.ResMap;
import math.vector.Vector3;
import com.ntunin.cybervision.game.Game;
import com.ntunin.cybervision.injector.Injectable;


/**
 * Created by nikolay on 02.02.17.
 */

public class CompassHandler implements SensorEventListener, Injectable {

    private SensorManager sensorManager; //Менеджер сенсоров аппрата
    private int cnt = 1;
    private Vector3 compassData = new Vector3();

    public CompassHandler() {
        reset();
        sensorManager = (SensorManager) Game.current()
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).size() != 0) {
            Sensor compass = sensorManager.getSensorList(
                    Sensor.TYPE_MAGNETIC_FIELD).get(0);
            sensorManager.registerListener(this, compass,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    private void reset() {
        compassData.x = 0;
        compassData.y = 0;
        compassData.z = 0;
        cnt = 1;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] v = event.values;
        compassData.x += v[0];
        compassData.y += v[1];
        compassData.z += v[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public Vector3 getMagnetData() {
        Vector3 v = new Vector3(compassData.x/cnt, compassData.y/cnt, compassData.z/cnt);
        reset();
        return v;
    }

    @Override
    public void init(ResMap<String, Object> data) {

    }
}
