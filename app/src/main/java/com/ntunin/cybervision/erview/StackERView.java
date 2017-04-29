package com.ntunin.cybervision.erview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.android.io.Accelerometer;
import com.ntunin.cybervision.android.io.Gyroscope;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.journal.cameracapturing.ImageFrame;
import com.ntunin.cybervision.objectfactory.ObjectFactory;
import com.ntunin.cybervision.opengl.screen.CameraView;
import com.ntunin.cybervision.opengl.screen.GLScreenView;
import com.ntunin.cybervision.opengl.screen.ImageFrameView;

import java.util.Timer;
import java.util.TimerTask;


import math.intsize.Size;
import math.vector.Vector3;


/**
 * Created by mikhaildomrachev on 17.04.17.
 */

public class StackERView extends ERView {

    private ImageFrameView frameView;
    private GLScreenView screenView;
    private ERView view;
    private Vector3 acceleration;
    private Vector3 rotation;
    private ImageFrame frame;
    private Accelerometer accelerometer;
    private Gyroscope gyroscope;


    public StackERView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        if(this.isInEditMode()) {
            return;
        }
        this.view = this;
        Injector injector = Injector.main();
        frameView = new CameraView(context);
        screenView = new GLScreenView(context);
        screenView.setBackgroundColor(0x0000ff);
        view.addView(frameView);
        view.addView(screenView);

    }

    @Override
    public void start() {
        if(this.isInEditMode()) {
            return;
        }
        startAccelerometer();
        startGyroscope();
        startFrameView();
    }

    private void startAccelerometer() {
        Injector injector = Injector.main();
        accelerometer = (Accelerometer) injector.getInstance(R.string.accelerometer);
        accelerometer.start();
    }

    private void startGyroscope() {
        Injector injector = Injector.main();
        gyroscope = (Gyroscope)injector.getInstance(R.string.gyroscope);
        gyroscope.start();
    }

    private void startFrameView() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Injector injector = Injector.main();
                ObjectFactory factory = (ObjectFactory) injector.getInstance(R.string.object_factory);
                Size size = (Size) factory.get(R.string.int_size).init(view.getWidth(), view.getHeight());
                Injector.main().setInstance(R.string.view_size, size);
                frameView.start();
            }
        }, 500);
    }




}
