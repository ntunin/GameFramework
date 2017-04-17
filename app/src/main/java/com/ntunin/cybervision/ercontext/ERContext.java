package com.ntunin.cybervision.ercontext;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ERContext extends Activity {
    protected static ERContext current;
    public static ERContext current() {
        return current;
    }
    public static void setCurrent(ERContext g) {
        current = g;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        current = this;
    }
}
