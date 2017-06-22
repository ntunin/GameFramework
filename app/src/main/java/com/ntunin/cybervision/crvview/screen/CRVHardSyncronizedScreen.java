package com.ntunin.cybervision.crvview.screen;

import android.content.Context;
import android.hardware.SensorManager;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.android.io.CRVAccelerometerSensor;
import com.ntunin.cybervision.android.io.CRVCompassSensor;
import com.ntunin.cybervision.android.io.CRVGyroscopeSensor;
import com.ntunin.cybervision.android.io.CRVHardwareContextSensor;
import com.ntunin.cybervision.crvcontext.CRVContext;
import com.ntunin.cybervision.crvcontext.CRVScreen;
import com.ntunin.cybervision.crvinjector.Injectable;
import com.ntunin.cybervision.crvinjector.CRVInjector;
import com.ntunin.cybervision.io.xfile.XFile;
import com.ntunin.cybervision.virtualmanagement.crvcamera.CRVCamera;
import com.ntunin.cybervision.virtualmanagement.crvcamera.CRVPerspectiveCamera;
import com.ntunin.cybervision.virtualmanagement.crvactor.CRVSkin.CRVSkin;
import com.ntunin.cybervision.virtualmanagement.crvlight.CRVDirectionalLight;
import com.ntunin.cybervision.res.ResMap;


import javax.microedition.khronos.opengles.GL10;

import math.vector.Vector3;


/**
 * Created by nik on 28.04.17.
 */

public abstract class CRVHardSyncronizedScreen extends CRVScreen implements Injectable {
    protected GL10 gl;
    static final float NS2S = 1.0f / 1000000000.0f;
    protected Vector3 globalPosition;
    protected Vector3 globalRotation;
    private CRVAccelerometerSensor accelerometer;
    private CRVGyroscopeSensor gyroscope;
    private CRVCompassSensor compass;

    private SensorManager sensorManager;
    private final float[] accelerationData = new float[3];
    private final float[] rotationData = new float[3];
    protected final float[] orientation = new float[3];
    protected final float[] compassData = new float[3];
    private CRVCamera crvCamera;
    private int MAX_TEST_ACCELERATION_MEASUREMENT_COUNT = 10;
    private int testAccelerationMeasurementCount = 0;
    protected final float[] linearAcceleration = new float[16];
    private float[] gravity = new float[3];
    private float[] rotationMatrix = new float[16];
    private float[] I = new float[16];
    private float[] deviceRelativeAcceleration = new float[4];
    private float[] inv = new float[16];

    protected float[] inertia = new float[3];

    protected float[] velocity = new float[3];
    protected float[] position = new float[3];

    @Override
    public void update(float deltaTime) {

        readSensor(accelerometer, accelerationData);
        readSensor(gyroscope, rotationData);
        readSensor(compass, compassData);

        if(!isSensorAvailable()) {
            return;
        }
        calibrateA();

        if(!checkMeasurement()) {
            return;
        }

        deviceRelativeAcceleration = new float[4];
        deviceRelativeAcceleration[0] = accelerationData[0];
        deviceRelativeAcceleration[1] = accelerationData[1];
        deviceRelativeAcceleration[2] = accelerationData[2];
        deviceRelativeAcceleration[3] = 0;

        SensorManager.getRotationMatrix(rotationMatrix, I, gravity, compassData);
        android.opengl.Matrix.invertM(inv, 0, rotationMatrix, 0);
        android.opengl.Matrix.multiplyMV(linearAcceleration, 0, inv, 0, deviceRelativeAcceleration, 0);
        SensorManager.getOrientation(rotationMatrix, orientation);

        linearAcceleration[0] = linearAcceleration[0] + inertia[0];
        linearAcceleration[1] = linearAcceleration[1] + inertia[1];
        linearAcceleration[2] = linearAcceleration[2] + inertia[2];


        velocity[0] += (linearAcceleration[0]) * deltaTime;
        velocity[1] += (linearAcceleration[1]) * deltaTime;
        velocity[2] += (linearAcceleration[2]) * deltaTime;

        inertia[0] =  - Math.signum(velocity[0]) * velocity[0] * velocity[0]/ 100000;
        inertia[1] =  - Math.signum(velocity[1]) * velocity[1] * velocity[1]/ 100000;
        inertia[2] =  - Math.signum(velocity[2]) * velocity[2] * velocity[2]/ 100000;


        position[0] += velocity[0] * deltaTime;
        position[1] += velocity[1] * deltaTime;
        position[2] += velocity[2] * deltaTime;
    }


    private void readSensor(CRVHardwareContextSensor sensor, float[] buffer) {
        float[] data = sensor.getData();
        for(int i = 0; data != null && i < buffer.length && i < data.length; i++) {
            buffer[i] = data[i];
        }
    }


    private boolean isSensorAvailable() {
        return  accelerationData[0] * accelerationData[0] +
                accelerationData[1] * accelerationData[1] +
                accelerationData[2] * accelerationData[2] != 0;
    }

    private void calibrateA() {
        calibrateA(0);
        calibrateA(1);
        calibrateA(2);
    }

    private void calibrateA(int i) {
        final float alpha = 0.8f;
        gravity[i] = alpha * gravity[i] + (1 - alpha) * accelerationData[i];
        accelerationData[i] -= gravity[i];
    }

    private boolean checkMeasurement() {
        return MAX_TEST_ACCELERATION_MEASUREMENT_COUNT < testAccelerationMeasurementCount++;
    }

    private float round(float v, int r) {
        return (int)(v * 100) * 1f/r;
    }

    private float round(double v, int r) {
        return (int)(v * 100) * 1f/r;
    }


    @Override
    public void present(float deltaTime) {
        preparePresentation();
        transformViaGlobal(deltaTime);
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void preparePresentation() {
        clear();
        crvCamera.motor();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    private void transformViaGlobal(float deltaTime) {
        gl.glTranslatef(globalPosition.x, globalPosition.y, globalPosition.z);
        gl.glPushMatrix();
        synchronizeAfterGlobalTransform(deltaTime);
        gl.glPopMatrix();
    }

    private void synchronizeAfterGlobalTransform(float deltaTime) {
        gl.glRotatef(rad(orientation[1]), 0, 1, 0);
        gl.glRotatef(rad(orientation[2]), 1, 0, 0);
        gl.glPushMatrix();
        presentAfterSync(deltaTime);
        gl.glPopMatrix();

    }





    protected abstract void presentAfterSync(float deltaTime);

    private void clear() {
        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    private void rotate() {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }



    private float rad(float grad) {
        return (float) (grad * 180 / Math.PI);
    }

    @Override
    public void pause() {
        //gl.glPopMatrix();
    }

    @Override
    public void resume() {
        setupThis();
        setupCamera();
        setupLight();
    }

    private void setupThis() {
        accelerometer = (CRVAccelerometerSensor) getRunningSensor(R.string.accelerometer_sensor);
        gyroscope = (CRVGyroscopeSensor) getRunningSensor(R.string.gyroscope_sensor);
        compass = (CRVCompassSensor) getRunningSensor(R.string.compass_sensor);
        sensorManager = (SensorManager) CRVContext.current().getSystemService(Context.SENSOR_SERVICE);
        gl = (GL10) CRVContext.get(R.string.gl);
    }

    CRVHardwareContextSensor getRunningSensor(int id) {
        CRVHardwareContextSensor sensor = (CRVHardwareContextSensor)CRVContext.get(id);
        sensor.startSensorTracking();
        return sensor;
    }

    private void setupCamera() {
        if(crvCamera == null) {
            crvCamera = new CRVPerspectiveCamera();
        }
        crvCamera.motor();
    }

    private void setupLight() {
        CRVDirectionalLight glLight = new CRVDirectionalLight(1);
        glLight.setDiffuse(0, 0, 1, 1);
        glLight.setDirection(0, 1, 0);
        glLight.enable();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void init(ResMap<String, Object> data) {
        ResMap<String, Object> argument = (ResMap<String, Object>) data.get("argument");
        crvCamera = (CRVCamera) argument.get("camera");
        globalPosition = new Vector3();
    }




}
