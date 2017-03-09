package com.ntunin.cybervision.opengl.screen;

import javax.microedition.khronos.opengles.GL10;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;


import javax.microedition.khronos.egl.EGLConfig;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.Size;
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
import com.ntunin.cybervision.journal.featureddetector.NautilusAlgorithm;
import com.ntunin.cybervision.io.FileIO;
import com.ntunin.cybervision.io.Input;


import java.util.Map;

/**
 * Created by nick on 01.03.16.
 */
public abstract class CVGLGame extends Activity implements Game {

    private static CVGLGame current;

    public static CVGLGame current() {
        return current;
    }

    public abstract void resume();

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();

    public abstract void dispose();

    enum GLGameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle
    }

    GLSurfaceView glView;
    GLGraphics glGraphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    AccelerometerHandler accelerometer;
    CompassHandler compass;
    GLGameState state = GLGameState.Initialized;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();
    WakeLock wakeLock;
    Injector injector;
    Map<String, Object> settings;
    private CameraView mOpenCvCameraView;
    private NautilusAlgorithm nautilus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector = Injector.main();
       // setWindowFeature();
       //setFlags();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_cvgl);
        mOpenCvCameraView = (CameraView) findViewById(R.id.camera_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        //setView();
        createGraphics();
        exportInputs();
        current = this;
        onSurfaceCreated(this.getGLGraphics().gl, null);
        onSurfaceCreated(this.getGLGraphics().gl, null);
    }

    private void createGraphics() {
        glGraphics = GLGraphics.create(glView);
    }

    private void setView() {
        glView = createGLView();
        setContentView(glView);
    }

    private void setWindowFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void setFlags() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void exportInputs() {

        fileIO = new AndroidFileIO(getAssets());
        injector.setInstance("File", fileIO);

        audio = new AndroidAudio(this);
        injector.setInstance("Audio", audio);

        input = new AndroidInput(this, mOpenCvCameraView, 1, 1);
        injector.setInstance("Input", input);

        accelerometer = new AccelerometerHandler(this);
        injector.setInstance("Accelerometer", accelerometer);

        compass = new CompassHandler(this);
        injector.setInstance("Compass", compass);

        PowerManager powerManager = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "GLGame");
        injector.setInstance("Wake Lock", wakeLock);

        nautilus = (NautilusAlgorithm) injector.getInstance("Nautilus Algorithm");
    }

    private GLSurfaceView createGLView() {
        GLSurfaceView glView = new GLSurfaceView(this);
        return glView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //glView.onResume();
        wakeLock.acquire();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glGraphics.setGL(gl);
        synchronized (stateChanged) {
            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            injector = Injector.main();
            settings = (Map<String, Object>) injector.getInstance("Settings");
            int w = mOpenCvCameraView.getWidth();
            int h = mOpenCvCameraView.getHeight();
            Size size =  new Size(display.getWidth(), display.getHeight());
            settings.put("Camera Size", size);
            if (state == GLGameState.Initialized)
                screen = getStartScreen();
            state = GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
    }

    public void onDrawFrame(GL10 gl) {
        GLGameState state = null;
        synchronized (stateChanged) {
            state = this.state;
        }
        if (state == GLGameState.Running) {
            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            screen.update(deltaTime);
            screen.present(deltaTime);
        }
        if (state == GLGameState.Paused) {
            screen.pause();
            synchronized (stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }

        if (state == GLGameState.Finished) {
            screen.pause();
            screen.dispose();
            synchronized (stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }
    }

    @Override
    public void onPause() {
        synchronized (stateChanged) {
            if (isFinishing())
                state = GLGameState.Finished;
            else
                state = GLGameState.Paused;
            while (true) {
                try {
                    stateChanged.wait();
                    break;
                } catch (InterruptedException e) {
                }
            }
        }
        wakeLock.release();
        glView.onPause();
        super.onPause();
    }

    public GLGraphics getGLGraphics() {
        return glGraphics;
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
}

