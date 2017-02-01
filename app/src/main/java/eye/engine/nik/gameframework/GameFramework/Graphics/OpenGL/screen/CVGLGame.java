package eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.screen;

import javax.microedition.khronos.opengles.GL10;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;


import javax.microedition.khronos.egl.EGLConfig;

import eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.Audio.AndroidAudio;
import eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.IO.AccelerometerHandler;
import eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.IO.AndroidFileIO;
import eye.engine.nik.gameframework.GameFramework.AndroidGameFramework.IO.AndroidInput;
import eye.engine.nik.gameframework.GameFramework.Audio.Audio;
import eye.engine.nik.gameframework.GameFramework.Game.Game;
import eye.engine.nik.gameframework.GameFramework.Game.Screen;
import eye.engine.nik.gameframework.GameFramework.Graphics.Graphics;
import eye.engine.nik.gameframework.GameFramework.Graphics.Injector.Injector;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.HashMapJournal;
import eye.engine.nik.gameframework.GameFramework.Graphics.Journal.Journal;
import eye.engine.nik.gameframework.GameFramework.IO.FileIO;
import eye.engine.nik.gameframework.GameFramework.IO.Input;
import eye.engine.nik.gameframework.R;


import org.opencv.android.CameraGLSurfaceView;
import org.opencv.core.Point;
import org.opencv.core.Size;

import java.util.Map;

/**
 * Created by nick on 01.03.16.
 */
public abstract class CVGLGame extends Activity implements Game, CameraGLSurfaceView.Renderer {

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
    GLGameState state = GLGameState.Initialized;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();
    WakeLock wakeLock;
    Injector injector;
    Map<String, Object> settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowFeature();
        setFlags();
        setView();
        createGraphics();
        exportInputs();
        current = this;
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
        injector = Injector.main();

        fileIO = new AndroidFileIO(getAssets());
        injector.setInstance("File", fileIO);

        audio = new AndroidAudio(this);
        injector.setInstance("Audio", audio);

        input = new AndroidInput(this, glView, 1, 1);
        injector.setInstance("Input", input);

        accelerometer = new AccelerometerHandler(this);
        injector.setInstance("Accelerometer", accelerometer);

        PowerManager powerManager = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "GLGame");
        injector.setInstance("Wake Lock", wakeLock);
    }

    private GLSurfaceView createGLView() {
        GLSurfaceView glView = new GLSurfaceView(this);
        glView.setRenderer(this);
        return glView;
    }

    @Override
    public void onResume() {
        super.onResume();
        glView.onResume();
        wakeLock.acquire();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glGraphics.setGL(gl);
        synchronized (stateChanged) {
            injector = Injector.main();
            settings = (Map<String, Object>) injector.getInstance("Settings");
            Size size =  new Size(glView.getWidth(), glView.getHeight());
            settings.put("Camera Size", size);
            if (state == GLGameState.Initialized)
                screen = getStartScreen();
            state = GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    }

    @Override
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

