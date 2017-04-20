package com.ntunin.cybervision.ercontext;

import android.content.Context;
import android.content.pm.PackageManager;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.errno.ERRNO;

/**
 * Created by mikhaildomrachev on 20.04.17.
 */

public class CameraPermissionResolver extends GrantResolver {

    @Override
    protected boolean checkPermissionGranted() {
        CameraGrantedActivity activity = (CameraGrantedActivity)ERContext.current();
        PackageManager pm = ((Context)activity).getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return false;
        }
        return activity.isCameraGranted();
    }

    @Override
    protected void sendGrantRequest() {
        CameraGrantedActivity activity = (CameraGrantedActivity)ERContext.current();
        activity.cameraGrantedRequest(this);
    }
}
