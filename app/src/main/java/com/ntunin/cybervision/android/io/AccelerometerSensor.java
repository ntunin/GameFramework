package com.ntunin.cybervision.android.io;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.res.ResMap;

import math.vector.Vector3;

/**
 * Created by nik on 06.05.17.
 */

public class AccelerometerSensor extends HardwareContextSensor implements Injectable{

    @Override
    public void init(ResMap<String, Object> data) {
        setType(Sensor.TYPE_ACCELEROMETER);
    }
}
