package com.ntunin.cybervision.opengl.screen;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.opengl.graphics.GLGraphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mikhaildomrachev on 18.04.17.
 */

public class GLScreenView extends FrameLayout implements GLSurfaceView.Renderer{

    private GLSurfaceView glView;
    private GLGraphics glGraphics;
    private int state = R.string.context_state_initialized;
    private Object stateChanged = new Object();
    private Screen screen;
    private long startTime = System.nanoTime();

    public GLScreenView(Context context) {
        super(context);

        glView = new GLSurfaceView(context);
        glView.setRenderer(this);
        glGraphics = GLGraphics.create(glView);
        this.addView(glView);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        glGraphics.setGL(gl);
        synchronized (stateChanged) {
            if (state == R.string.context_state_initialized) {
                screen = (Screen) Injector.main().getInstance(R.string.screen);
            }
            state = R.string.context_state_running;
            screen.resume();
            startTime = System.nanoTime();
        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        int state = -1;
        synchronized (stateChanged) {
            state = this.state;
        }
        switch (state) {
            case R.string.context_state_running: {
                onRun();
                break;
            }
            case R.string.context_state_paused: {
                onPause();
                break;
            }
            case R.string.context_state_finished: {
                onFinish();
                break;
            }
        }
    }

    private void onRun() {
        float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
        startTime = System.nanoTime();
        screen.update(deltaTime);
        screen.present(deltaTime);
    }

    private void onPause() {
        screen.pause();
        synchronized (stateChanged) {
            this.state = R.string.context_state_idle;
            stateChanged.notifyAll();
        }
    }

    private void onFinish() {
        onPause();
        screen.dispose();
    }

}
