package com.ntunin.cybervision.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.errno.ErrorListener;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.opengl.screen.HardSyncronizedGLScreen;

import math.vector.Vector3;

/**
 * Created by nik on 29.04.17.
 */

public class ERStackViewTestActivity extends ERStackViewActivity implements ErrorListener {

    private TextView aLabel;
    private TextView vLabel;
    private TextView xLabel;
    private TextView rLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ERRNO.subscribe(new int[]{
                R.string.no_accelerometer,
                R.string.no_gyroscope,
                R.string.camera_connection_error,
                R.string.camera_not_granted,
                R.string.not_init
        }, this);
        aLabel = (TextView) findViewById(R.id.aLabel);
        vLabel = (TextView) findViewById(R.id.vLabel);
        xLabel = (TextView) findViewById(R.id.xLabel);
        rLabel = (TextView) findViewById(R.id.rLabel);
    }

    @Override
    protected Screen getScreen() {
        MyGameScreen screen = new MyGameScreen();
        screen.setTestContext(this);
        return screen;
    }

    @Override
    public void onError(String error) {
        showLongToast(error);
    }

    private void showLongToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void setAcceleration(Vector3 acceleration) {
        if(acceleration != null) {
            aLabel.setText(acceleration.toString());
        }
    }

    public void setVelocity(Vector3 velocity) {
        if(velocity != null) {
            vLabel.setText(velocity.toString());
        }
    }

    public void setPosition(Vector3 position) {
        if(position != null) {
            xLabel.setText(position.toString());
        }
    }

    public void setRotation(Vector3 rotation) {
        if(rotation!= null) {
            rLabel.setText(rotation.toString());
        }
    }
}
