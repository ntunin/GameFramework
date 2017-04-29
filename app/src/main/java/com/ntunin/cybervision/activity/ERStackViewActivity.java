package com.ntunin.cybervision.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.AccelerometerGrantedActivity;
import com.ntunin.cybervision.ercontext.CameraGrantedActivity;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.ercontext.GrantResolver;
import com.ntunin.cybervision.ercontext.GyroscopeGrantedActivity;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.erview.ERView;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.opengl.screen.HardSyncronizedGLScreen;

/**
 * Created by mikhaildomrachev on 17.04.17.
 */

public abstract class ERStackViewActivity extends ERContext implements CameraGrantedActivity, AccelerometerGrantedActivity, GyroscopeGrantedActivity {

    private ERView view;
    private GrantResolver cameraResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er);
        view = (ERView) findViewById(R.id.er_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.start();
        String error = ERRNO.last();
        if(error != null) {
            Toast toast = Toast.makeText(this, error, Toast.LENGTH_LONG);
            toast.show();
        }
        ERRNO.write(null);
    }

    @Override
    public void cameraGrantedRequest(GrantResolver resolver) {
        this.cameraResolver = resolver;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                R.string.camera_permission);

    }

    @Override
    public boolean isCameraGranted() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case R.string.camera_permission: {
                boolean result = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;

                cameraResolver.onPermissionGrantedResult(result);
            }
        }
    }


    @Override
    public boolean isGyroscopeAvailable() {
        PackageManager manager = getPackageManager();
        boolean hasGyroscope = manager.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE);
        return hasGyroscope;
    }

    @Override
    public boolean isAccelerometerAvailable() {
        PackageManager manager = getPackageManager();
        boolean hasAccelerometer = manager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        return hasAccelerometer;
    }
}
