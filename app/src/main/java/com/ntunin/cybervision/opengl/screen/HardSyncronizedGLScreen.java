package com.ntunin.cybervision.opengl.screen;

import android.content.Context;
import android.hardware.SensorManager;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.android.io.AccelerometerSensor;
import com.ntunin.cybervision.android.io.CompassSensor;
import com.ntunin.cybervision.android.io.GyroscopeSensor;
import com.ntunin.cybervision.android.io.HardwareContextSensor;
import com.ntunin.cybervision.ercontext.Body;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.ercontext.Operator;
import com.ntunin.cybervision.ercontext.SceneContext;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.ercontext.World;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.opengl.actor.GLActor;
import com.ntunin.cybervision.opengl.camera.Camera;
import com.ntunin.cybervision.opengl.camera.PerspectiveCamera;
import com.ntunin.cybervision.opengl.graphics.GLDress;
import com.ntunin.cybervision.opengl.light.GLDirectionalLight;
import com.ntunin.cybervision.opengl.light.GLGaffer;
import com.ntunin.cybervision.opengl.light.GLLight;
import com.ntunin.cybervision.opengl.motion.GLTransition;
import com.ntunin.cybervision.res.ResMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import math.vector.Vector3;

/**
 * Created by nik on 28.04.17.
 */

public abstract class HardSyncronizedGLScreen extends Screen implements Injectable {
    protected GL10 gl;
    static final float NS2S = 1.0f / 1000000000.0f;

    private AccelerometerSensor accelerometer;
    private GyroscopeSensor gyroscope;
    private CompassSensor compass;

    private SensorManager sensorManager;
    private final float[] rotationMatrix = new float[9];
    private final float[] accelerationData = new float[3];
    private final float[] rotationData = new float[3];
    private final float[] orientationData = new float[3];
    private final float[] compassData = new float[3];
    private Camera glCamera;
    @Override
    public void update(float deltaTime) {
        readSensors();
        getOrientation();

        updateAfterSync(deltaTime);
    }

    private void readSensors() {
        readSensor(accelerometer, accelerationData);
        readSensor(gyroscope, rotationData);
        readSensor(compass, compassData);
    }

    private void readSensor(HardwareContextSensor sensor, float[] buffer) {
        float[] data = sensor.getData();
        for(int i = 0; data != null && i < buffer.length && i < data.length; i++) {
            buffer[i] = data[i];
        }
    }

    private void getOrientation() {
        sensorManager.getRotationMatrix(rotationMatrix, null,
                accelerationData, compassData);
        sensorManager.getOrientation(rotationMatrix, orientationData);
    }


    public abstract void updateAfterSync(float deltaTime);

    @Override
    public void present(float deltaTime) {
        clear();
        rotate();
        presentAfterSync(deltaTime);
    }

    private void clear() {
        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    private void rotate() {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glRotatef(rad(orientationData[0]), 0, 0, 1);
        gl.glRotatef(rad(orientationData[1]), 1, 0, 0);
        gl.glRotatef(rad(orientationData[2]), 0, 1, 0);
        gl.glPushMatrix();
    }



    private float rad(float grad) {
        return (float) (grad * 180 / Math.PI);
    }


    public abstract void presentAfterSync(float deltaTime);

    @Override
    public void pause() {
        gl.glPopMatrix();
    }

    @Override
    public void resume() {
        setupThis();
        setupCamera();
        setupLight();
    }

    private void setupThis() {
        accelerometer = (AccelerometerSensor) getRunningSensor(R.string.accelerometer_sensor);
        gyroscope = (GyroscopeSensor) getRunningSensor(R.string.gyroscope_sensor);
        compass = (CompassSensor) getRunningSensor(R.string.compass_sensor);
        sensorManager = (SensorManager) ERContext.current().getSystemService(Context.SENSOR_SERVICE);
        gl = (GL10) ERContext.get(R.string.gl);
    }

    HardwareContextSensor getRunningSensor(int id) {
        Injector injector = Injector.main();
        HardwareContextSensor sensor = (HardwareContextSensor) injector.getInstance(id);
        sensor.startSensorTracking();
        return sensor;
    }

    private void setupCamera() {
        glCamera = new PerspectiveCamera();
        glCamera.motor();
    }

    private void setupLight() {
        GLDirectionalLight glLight = new GLDirectionalLight(1);
        glLight.setDiffuse(0, 0, 1, 1);
        glLight.setDirection(0, 1, 0);
        glLight.enable();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void init(ResMap<String, Object> data) {
    }




}
