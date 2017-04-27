package com.ntunin.cybervision.ercontext;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.errno.ERRNO;

/**
 * Created by mikhaildomrachev on 20.04.17.
 */

public class MotionSensorPermissionResolver extends GrantResolver {
    @Override
    protected void sendGrantRequest() {
        ERRNO.write(R.string.no_grant_request);
    }

    @Override
    protected boolean checkPermissionGranted() {
        MotionSensorGrantedActivity activity = (MotionSensorGrantedActivity) ERContext.current();
        return activity.isSensorsAvailable();
    }
}
