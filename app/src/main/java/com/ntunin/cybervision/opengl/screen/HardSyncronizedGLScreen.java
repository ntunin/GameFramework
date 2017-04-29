package com.ntunin.cybervision.opengl.screen;

import android.util.Log;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.android.io.Accelerometer;
import com.ntunin.cybervision.android.io.Gyroscope;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.res.ResMap;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import math.vector.Vector3;

/**
 * Created by nik on 28.04.17.
 */

public class HardSyncronizedGLScreen extends Screen implements Injectable {
    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    protected GL10 gl;

    @Override
    public void update(float deltaTime) {
        Vector3 acceleration = accelerometer.getAcceleration();
        Vector3 rotation = gyroscope.getTransform();
        Log.d("Screen", acceleration.toString());
        return;
    }

    @Override
    public void present(float deltaTime) {
        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        Injector injector = Injector.main();
        accelerometer = (Accelerometer) injector.getInstance(R.string.accelerometer);
        gyroscope = (Gyroscope) injector.getInstance(R.string.gyroscope);
        gl = (GL10) injector.getInstance(R.string.gl);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void init(ResMap<String, Object> data) {
        return;
    }
}
