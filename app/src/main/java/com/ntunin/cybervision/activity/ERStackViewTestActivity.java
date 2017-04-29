package com.ntunin.cybervision.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.errno.ErrorListener;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.opengl.screen.HardSyncronizedGLScreen;

/**
 * Created by nik on 29.04.17.
 */

public class ERStackViewTestActivity extends ERStackViewActivity implements ErrorListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ERRNO.subscribe(new int[]{
                R.string.no_accelerometer,
                R.string.no_gyroscope,
                R.string.camera_connection_error,
                R.string.camera_not_granted
        }, this);
    }

    @Override
    protected Screen getScreen() {
        HardSyncronizedGLScreen screen = (HardSyncronizedGLScreen) Injector.main().getInstance(R.string.hard_sync_screen);
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
}
