package com.ntunin.cybervision.android.io;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.ercontext.GrantListener;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.res.ResMap;

import math.vector.Vector3;

/**
 * Created by nik on 27.04.17.
 */

public class Gyroscope extends Sensor3D implements Injectable {
    private int cnt;
    private ObjectFactory factory;
    private Vector3 transform;



    public Vector3 getTransform() {
        Vector3 result = (Vector3) factory.get(R.string.vector3).init(
                transform.x/cnt,
                transform.y/cnt,
                transform.z/cnt);
        reset();
        return result;
    }
    public void start() {
        ERContext.current().grantRequest(R.string.gyroscope_permission, new GrantListener() {
            @Override
            public void onPermissionGrantedResult(boolean result) {
                if(result == true) {
                    register(Sensor.TYPE_GYROSCOPE);
                } else {
                    ERRNO.write(R.string.no_gyroscope);
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        transform.x += x;
        transform.y += y;
        transform.z += z;
    }

    @Override
    public void init(ResMap<String, Object> data) {
        factory = (ObjectFactory) data.get(R.string.object_factory);
        reset();
    }

    private void reset() {

        cnt = 1;
        if(transform!=null) {
            transform.release();
        }
        transform = (Vector3) factory.get(R.string.vector3).init();
    }
}
