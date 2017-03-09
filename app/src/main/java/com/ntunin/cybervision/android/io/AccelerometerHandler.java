package com.ntunin.cybervision.android.io;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ntunin.cybervision.Vector3;


public class AccelerometerHandler implements SensorEventListener {

    private final int MAX_CALIBRATION_COUNT = 10;
    private Vector3 dA = new Vector3();
    private Vector3 a = new Vector3();
    private int cnt;
    private int calibrationCount;


    public Vector3 getAcceleration() {
        Vector3 point = new Vector3(a.x/cnt, a.y/cnt, a.z/cnt);
        reset();
        return point;
    }

    public void reset() {
        cnt = 1;
        a.x = 0;
        a.y = 0;
        a.z = 0;
    }


    public AccelerometerHandler(Context context) {
        reset();
        SensorManager manager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = manager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
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
}