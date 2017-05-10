package com.ntunin.cybervision.erview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.ERContext;
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


    public StackERView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        if(this.isInEditMode()) {
            return;
        }
        this.view = this;
        frameView = new CameraView(context);
        screenView = new GLScreenView(context);
        view.addView(frameView);
        view.addView(screenView);

    }

    @Override
    public void start() {
        if(this.isInEditMode()) {
            return;
        }
        startFrameView();
    }

    private void startFrameView() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Injector injector = Injector.main();
                Size size = (Size) ERContext.create(R.string.int_size).init(view.getWidth(), view.getHeight());
                ERContext.set(R.string.view_size, size);
                frameView.start();
            }
        }, 500);
    }




}
