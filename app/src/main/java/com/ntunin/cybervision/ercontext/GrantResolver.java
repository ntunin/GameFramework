package com.ntunin.cybervision.ercontext;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.res.ResMap;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mikhaildomrachev on 20.04.17.
 */

public abstract class GrantResolver implements Injectable {

    List<GrantListener> listeners = new LinkedList<>();

    public boolean isGranted() {
        try {
            return checkPermissionGranted();
        } catch (Exception e) {
            ERRNO.write(R.string.method_not_supported);
            return false;
        }
    }

    public void grantRequest(GrantListener listener) {
        listeners.add(listener);
        try {
            sendGrantRequest();
        }catch (Exception e) {
            ERRNO.write(R.string.method_not_supported);
            onPermissionGrantedResult(false);
        }
    }

    protected abstract void sendGrantRequest();
    protected abstract boolean checkPermissionGranted();

    public void onPermissionGrantedResult(boolean result) {
        for(GrantListener listener: listeners) {
            listener.onPermissionGrantedResult(result);
        }
    }

    @Override
    public void init(ResMap<String, Object> data) {
        return;
    }

}
