package com.ntunin.cybervision.erview;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.opengl.graphics.GLGraphics;
import com.ntunin.cybervision.opengl.screen.ImageFrameView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by mikhaildomrachev on 17.04.17.
 */

public class StackERView extends ERView implements JournalSubscriber, GLSurfaceView.Renderer {

    private ImageFrameView frameView;
    private GLSurfaceView glView;
    private GLGraphics glGraphics;
    private int state = R.string.context_state_initialized;
    private Object stateChanged = new Object();
    private Screen screen;
    private long startTime = System.nanoTime();

    public StackERView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        frameView = new ImageFrameView(context, attrs);
        this.addView(frameView);

        glView = new GLSurfaceView(context, attrs);
        glView.setRenderer(this);
        glGraphics = GLGraphics.create(glView);
        this.addView(glView);
    }


    @Override
    public void breakingNews(BreakingNews news) {
        ImageFrame frame = (ImageFrame) news.read(R.string.image_frame);
        frameView.draw(frame);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        glGraphics.setGL(gl);
        synchronized (stateChanged) {
            if (state == R.string.context_state_initialized) {
                Injector injector = Injector.main();
                screen = (Screen) injector.getInstance("Screen");
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
