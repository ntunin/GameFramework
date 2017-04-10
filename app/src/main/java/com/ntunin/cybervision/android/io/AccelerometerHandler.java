package com.ntunin.cybervision.android.io;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ResMap;
import math.vector.Vector3;
import com.ntunin.cybervision.game.Game;
import com.ntunin.cybervision.injector.Injectable;


public class AccelerometerHandler implements SensorEventListener, Injectable {

    private final int MAX_CALIBRATION_COUNT = 10;
    private Vector3 dA;
    private Vector3 a;
    private int cnt;
    private int calibrationCount;
    private ObjectFactory factory;


    public Vector3 getAcceleration() {
        Vector3 point = (Vector3) factory.get(R.string.vector3).init(a.x/cnt, a.y/cnt, a.z/cnt);
        reset();
        return point;
    }

    public void reset() {
        cnt = 1;
        if(a!=null) {
            a.release();
        }
        a = (Vector3) factory.get(R.string.vector3).init();
    }


    public AccelerometerHandler() {

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// Здесь ничего не делаем
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if(MAX_CALIBRATION_COUNT > calibrationCount) {
            calibrationCount++;
            dA.add(x, y, z);
        } else if(MAX_CALIBRATION_COUNT == calibrationCount) {
            calibrationCount++;
            dA = dA.mul(-1f / MAX_CALIBRATION_COUNT);
        } else {
            a.x = x + dA.x;
            a.y = y + dA.y;
            a.z = z + dA.z;

            cnt++;
        }
    }

    @Override
    public void init(ResMap<String, Object> data) {
        factory = (ObjectFactory) data.get(R.string.object_factory);
        dA = (Vector3) factory.get(R.string.vector3).init();
        reset();
        SensorManager manager = (SensorManager) Game.current()
                .getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = manager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }
}