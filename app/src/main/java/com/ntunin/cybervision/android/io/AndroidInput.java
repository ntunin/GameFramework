package com.ntunin.cybervision.android.io;

import java.util.List;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.ntunin.cybervision.io.Input;


public class AndroidInput implements Input {
    Accelerometer accelHandler;
    KeyboardHandler keyHandler;
    TouchHandler touchHandler;
    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        accelHandler = new Accelerometer();
        keyHandler = new KeyboardHandler(view);
        if(Integer.parseInt(VERSION.SDK) < 5)
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        else
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }
    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyHandler.isKeyPressed(keyCode);
    }
    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }
    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }
    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
    @Override
    public List<KeyEvent> getKeyEvents() {
        return keyHandler.getKeyEvents();
    }
}