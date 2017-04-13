package com.ntunin.cybervision.opengl.screen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.ntunin.cybervision.ObjectFactory;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.Res;
import com.ntunin.cybervision.ResMap;
import com.ntunin.cybervision.android.audio.AndroidAudio;
import com.ntunin.cybervision.android.io.AccelerometerHandler;
import com.ntunin.cybervision.android.io.AndroidFileIO;
import com.ntunin.cybervision.android.io.AndroidInput;
import com.ntunin.cybervision.android.io.CompassHandler;
import com.ntunin.cybervision.audio.Audio;
import com.ntunin.cybervision.game.Game;
import com.ntunin.cybervision.game.Screen;
import com.ntunin.cybervision.graphics.Graphics;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.io.Input;
import com.ntunin.cybervision.journal.cameracapturing.JournalingCameraCapturing;

import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import math.intsize.Size;

/**
 * Created by mikhaildomrachev on 05/04/2017.
 */

public class CyberVisionGame extends Game {

    public static final int PERMISSION_REQUEST_CODE_CAMERA = 101;

    GLSurfaceView glView;
    GLGraphics glGraphics;


    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    AccelerometerHandler accelerometer;
    CompassHandler compass;
    ResMap<String, Object> settings;
    CVGLGame.GLGameState state = CVGLGame.GLGameState.Initialized;


    Injector injector;


    private CameraView mOpenCvCameraView;


    PowerManager.WakeLock wakeLock;
    private ObjectFactory factory;


    Object stateChanged = new Object();
    long startTime = System.nanoTime();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current = this;
        setContentView(R.layout.activity_cvgl);
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
        mOpenCvCameraView = (CameraView) findViewById(R.id.camera_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        injectDependencies();
        startCapturingIfPossible();
    }

    private void startCapturingIfPossible() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE_CAMERA);
        } else {
            start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    start();
                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void injectDependencies() {
        injector = Injector.main();
        factory = (ObjectFactory) injector.getInstance(R.string.object_factory);
        settings = (ResMap<String, Object>) injector.getInstance(R.string.settings);

        fileIO = new AndroidFileIO(getApplicationContext().getAssets());
        injector.setInstance("File", fileIO);


        PowerManager powerManager = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "GLGame");
        injector.setInstance("Wake Lock", wakeLock);

    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        throw new IllegalStateException("We are using OpenGL!");
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    @Override
    public Screen getCurrentScreen() {
        return screen;
    }

    @Override
    public Screen getStartScreen() {
        return new CVGLScreen();
    }

    @Override
    public void onResume() {
        super.onResume();
        //glView.onResume();
        wakeLock.acquire();
    }

    private void start() {
        //setupGL();
        startCapturing();
    }

    private void setupGL() {
        glView = new GLSurfaceView(this);
        glGraphics = GLGraphics.create(glView);
        onSurfaceCreated(glGraphics.gl, null);
    }

    private void startCapturing() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Size size = (Size) factory.get(R.string.int_size).init(display.getWidth(), display.getHeight());
        settings.put(R.string.camera_size, size);
        JournalingCameraCapturing camera = (JournalingCameraCapturing) injector.getInstance(R.string.camera);
        camera.start();
    }



    public GLGraphics getGLGraphics() {
        return glGraphics;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glGraphics.setGL(gl);
        synchronized (stateChanged) {
            int w = mOpenCvCameraView.getWidth();
            int h = mOpenCvCameraView.getHeight();
            if (state == CVGLGame.GLGameState.Initialized)
                screen = getStartScreen();
            state = CVGLGame.GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }
    }

}
