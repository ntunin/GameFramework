package com.ntunin.cybervision.ercontext;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.errno.ERRNO;

/**
 * Created by nik on 27.04.17.
 */

public class AccelerometerResolver extends GrantResolver {
    @Override
    protected void sendGrantRequest() {
        onPermissionGrantedResult(checkPermissionGranted());
    }

    @Override
    protected boolean checkPermissionGranted() {
        AccelerometerGrantedActivity activity = (AccelerometerGrantedActivity) ERContext.current();
        return activity.isAccelerometerAvailable();
    }
}
