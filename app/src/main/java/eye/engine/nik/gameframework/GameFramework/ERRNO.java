package eye.engine.nik.gameframework.GameFramework;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nikolay on 05.10.16.
 */

public class ERRNO {
    private static ERRNO errno;
    List<Integer> list;
    Map<String, Integer> numbers;
    Map<Integer, String> descriptions;

    public static int last() {
        if(errno == null) errno = new ERRNO();
        return errno._last();
    }

    private ERRNO() {
        list = new ArrayList<>();
        numbers = new HashMap<>();
        descriptions = new HashMap<>();
        add(101, "File not found");
        add(102, "Cannot read file");
        add(103, "Read out end of stream");
        add(201, "No such method");
        add(202, "Invocation error");
        add(203, "Illegal access");
        add(301, "Parse Int");
        add(302, "Parse Float");
        add(400, "Cast");
        add(500, "Have not decorations");
        add(1, "Exception");
    }
    private void add(int number, String description) {
        numbers.put(description, number);
        descriptions.put(number, description);
    }
    private int _last() {
        int index = list.size() - 1;
        if(index < 0) return 0;
        return list.get(index);
    }
    public static void write(String description) {
        if(errno == null) errno = new ERRNO();
        errno._write(description);
    }
    private  void _write(String desctiption) {
        int no = numbers.get(desctiption);
        list.add(no);
    }

}
