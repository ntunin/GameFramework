package com.ntunin.cybervision.journal.motionsensor;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.android.io.Accelerometer;
import com.ntunin.cybervision.android.io.Gyroscope;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.res.ResMap;

import java.util.Timer;
import java.util.TimerTask;

import math.vector.Vector3;

/**
 * Created by nik on 27.04.17.
 */

public class MotionSensor implements Injectable {
    private boolean running = false;
    private TimerTask defineMotionTask;
    private int delay = 100;
    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private Vector3 acceleration;
    private Vector3 rotation;

    @Override
    public void init(ResMap<String, Object> data) {
        delay = (int) data.get(R.string.motion_sensor_delay);

        Injector injector = Injector.main();
        accelerometer = (Accelerometer) data.get(R.string.accelerometer);
        gyroscope = (Gyroscope) data.get(R.string.gyroscope);
        TimerTask defineMotionTask = new TimerTask() {
            @Override
            public void run() {
                acceleration = accelerometer.getAcceleration();
                rotation = gyroscope.getTransform();
                handle();
            }
        };
    }

    public void start() {
        if(ERContext.current().isGranted(R.string.motion_sensor_permission)) {
            try {
                running = true;
                handle();
            } catch (NullPointerException e) {

            }
        } else {
            ERRNO.write(R.string.no_motion_sensor);
        }

    }

    private void handle() {
        new Timer().schedule(defineMotionTask, delay);
    }

    public void stop() {
        running = false;
    }
}
