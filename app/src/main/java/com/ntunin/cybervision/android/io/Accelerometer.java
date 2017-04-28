package com.ntunin.cybervision.android.io;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.ercontext.GrantListener;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.res.ResMap;
import math.vector.Vector3;

import com.ntunin.cybervision.injector.Injectable;


public class Accelerometer extends Sensor3D implements Injectable {

    private int maxCalibrationCount = 10;
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

    public void start() {
        ERContext.current().grantRequest(R.string.accelerometer_permission, new GrantListener() {
            @Override
            public void onPermissionGrantedResult(boolean result) {
                if(result == true) {
                    register(Sensor.TYPE_ACCELEROMETER);
                } else {
                    ERRNO.write(R.string.no_accelerometer);
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if(maxCalibrationCount > calibrationCount) {
            calibrationCount++;
            dA.add(x, y, z);
        } else if(maxCalibrationCount == calibrationCount) {
            calibrationCount++;
            dA = dA.mul(-1f / maxCalibrationCount);
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
        maxCalibrationCount = (int) data.get(R.string.max_a_calibration_count);
        reset();
    }
}