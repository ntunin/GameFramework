package com.ntunin.cybervision.res;

import android.content.Context;
import android.content.res.Resources;

import com.ntunin.cybervision.ercontext.ERContext;

/**
 * Created by mikhaildomrachev on 31/03/2017.
 */

public class Res {
    public static String string(int id) {
        Context context = ERContext.current();
        if(context == null) return null;
        String result = context.getResources().getString(id);
        return result;
    }

    public static String error(int id) {
        return string(id);
    }

    public static String[] array(int id) {
        Context context = ERContext.current();
        if(context == null) return null;
        String[] result = context.getResources().getStringArray(id);
        return result;
    }
}