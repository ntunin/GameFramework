package com.ntunin.cybervision.android.io;

import android.hardware.Sensor;

import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.res.ResMap;

import math.vector.Vector3;

/**
 * Created by nik on 07.05.17.
 */

public class CompassSensor extends HardwareContextSensor implements Injectable {
    @Override
    public void init(ResMap<String, Object> data) {
        setType(Sensor.TYPE_MAGNETIC_FIELD);
    }

}
