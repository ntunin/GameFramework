package com.ntunin.cybervision.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.erview.ERView;

/**
 * Created by mikhaildomrachev on 17.04.17.
 */

public class ERContextTestActivity extends ERContext {

    private ERView view;
    private PowerManager.WakeLock wakeLock;
    private static final int PERMISSION_REQUEST_CODE_CAMERA = 101;

    public void onResume() {
        super.onResume();
        startViewIfPossible();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er);
        view = (ERView) findViewById(R.id.er_view);
        wakeLock();
    }

    private void startViewIfPossible() {

        if (!isCameraGranted()) {
            requestCameraPermissions();
        } else {
            view.start();
        }
    }

    private boolean isCameraGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE_CAMERA);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    view.start();

                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void wakeLock() {
        PowerManager powerManager = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "GLGame");
    }
}
