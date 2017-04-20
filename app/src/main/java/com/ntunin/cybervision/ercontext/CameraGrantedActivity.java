package com.ntunin.cybervision.ercontext;

/**
 * Created by mikhaildomrachev on 20.04.17.
 */

public interface CameraGrantedActivity {
    boolean isCameraGranted();
    void cameraGrantedRequest(GrantResolver resolver);
}
