package com.ntunin.cybervision;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    private void fileNotFound() {

    }

}
