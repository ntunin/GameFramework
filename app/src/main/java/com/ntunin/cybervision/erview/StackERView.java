package com.ntunin.cybervision.erview;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.JournalSubscriber;
import com.ntunin.cybervision.journal.breakingnews.BreakingNews;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.journal.cameracapturing.JournalingCameraCapturing;
import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.opengl.graphics.GLGraphics;
import com.ntunin.cybervision.opengl.screen.CameraView;
import com.ntunin.cybervision.opengl.screen.GLScreenView;
import com.ntunin.cybervision.opengl.screen.ImageFrameView;
import com.ntunin.cybervision.res.ResMap;
import com.ntunin.cybervision.timeout.Task;
import com.ntunin.cybervision.timeout.Timeout;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import math.intsize.Size;


/**
 * Created by mikhaildomrachev on 17.04.17.
 */

public class StackERView extends ERView {

    private ImageFrameView frameView;
    private GLScreenView screenView;
    private Context context;
    private ERView view;

    public StackERView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.view = this;
        frameView = new CameraView(context);
        screenView = new GLScreenView(context);
        screenView.setBackgroundColor(0x0000ff);
        view.addView(frameView);
        view.addView(screenView);

    }

    @Override
    public void start() {
        Timeout.set(new Task() {
            @Override
            public void execute() {
                Injector injector = Injector.main();
                ObjectFactory factory = (ObjectFactory) injector.getInstance(R.string.object_factory);
                Size size = (Size) factory.get(R.string.int_size).init(view.getWidth(), view.getHeight());
                Injector.main().setInstance(R.string.view_size, size);
                frameView.start();
            }
        }, 10);
    }


}
