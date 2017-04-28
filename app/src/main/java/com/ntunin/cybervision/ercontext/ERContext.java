package com.ntunin.cybervision.ercontext;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.errno.ERRNO;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.res.ResMap;

import java.util.List;
import java.util.Map;

public abstract class ERContext extends Activity {
    protected static ERContext current;
    private PowerManager.WakeLock wakeLock;
    private ResMap<String, GrantResolver> resolvers;

    public static ERContext current() {
        if(current == null) {
            ERRNO.write(R.string.no_context);
        }
        return current;
    }
    public static void setCurrent(ERContext g) {
        current = g;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        current = this;
        resolvers = (ResMap<String, GrantResolver>) Injector.main().getInstance(R.string.grant_resolvers);
        if(resolvers == null) {
            ERRNO.write(R.string.no_resolvers);
        }
    }

    public boolean isGranted(int id) {
        GrantResolver resolver = (GrantResolver) resolvers.get(id);
        if(resolver == null) {
            ERRNO.write(R.string.no_grant_resolver);
            return false;
        }
        return resolver.isGranted();
    }

    public void grantRequest(int id, GrantListener listener) {
        GrantResolver resolver = (GrantResolver) resolvers.get(id);
        if(resolver == null) {
            ERRNO.write(R.string.no_grant_resolver);
            listener.onPermissionGrantedResult(false);
            return;
        }
        resolver.grantRequest(listener);
    }


    private void wakeLock() {
        PowerManager powerManager = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "GLGame");
    }

    public void catchFatal(String description) {
        ERRNO.write(description);
    }



}
