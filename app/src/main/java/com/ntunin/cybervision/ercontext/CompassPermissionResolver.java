package com.ntunin.cybervision.ercontext;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by mikhaildomrachev on 20.04.17.
 */

public class CompassPermissionResolver extends GrantResolver {
    @Override
    protected void sendGrantRequest() {

    }

    @Override
    protected boolean checkPermissionGranted() {
        return false;
    }
}
