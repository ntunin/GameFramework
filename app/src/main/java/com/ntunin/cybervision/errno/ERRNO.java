package com.ntunin.cybervision.errno;

import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.res.Res;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nikolay on 05.10.16.
 */

public class ERRNO {
    private static ERRNO errno;
    List<String> list;

    public static String last() {
        if(errno == null) errno = new ERRNO();
        return errno._last();
    }

    private ERRNO() {
        list = new LinkedList<>();
    }
    private String _last() {
        int index = list.size() - 1;
        if(index < 0) return null;
        return list.get(index);
    }
    public static void write(String description) {
        if(errno == null) errno = new ERRNO();
        errno._write(description);
    }

    public static void write(int id) {
        String description = Res.error(id);
        if(errno == null) errno = new ERRNO();
        errno._write(description);
    }

    private  void _write(String description) {
        list.add(description);
    }

    public static void fatal(int id) {
        fatal(Res.error(id));
    }

    public static void fatal(String description) {
        ERContext.current().catchFatal(description);
    }

    public static boolean isLast(String description) {
        if(errno == null) errno = new ERRNO();
        return errno._isLast(description);
    }

    public static boolean isLast(int id) {
        if(errno == null) errno = new ERRNO();
        return errno._isLast(Res.string(id));
    }

    private boolean _isLast(String description) {
        String last = _last();
        return last.equals(description);
    }




}
