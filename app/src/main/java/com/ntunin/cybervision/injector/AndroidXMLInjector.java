package com.ntunin.cybervision.injector;

import com.ntunin.cybervision.ERRNO;
import com.ntunin.cybervision.R;
import com.ntunin.cybervision.Res;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikolay on 01.04.17.
 */

public class AndroidXMLInjector extends MapInjector {
    public AndroidXMLInjector() {
        Map<String, Object> beans = loadBeans();
        instances = loadItems(beans);
    }

    private Map<String, Object> loadBeans() {
        Map<String, Object> beans = new HashMap<>();
        String[] resBeans = Res.array(R.array.beans);

        for(int i = 0; i < resBeans.length; i++) {
            String definition = resBeans[i].trim();
            int index = definition.indexOf(":");
            String typeAndName = definition.substring(0, index).trim();
            String content = definition.substring(index + 1).trim();
            index = typeAndName.indexOf(" ");
            String type = typeAndName.substring(0, index).trim();
            String name = typeAndName.substring(index).trim();
            Object o = null;
            switch (type) {
                case "Injectable":
                    o = createObject(content, beans);
                    break;
                case "Map":
                    o = createMap(content, beans);
                    break;
                case "Integer":
                    o = createInteger(content);
                    break;
            }
            beans.put(name, o);
        }
        return beans;
    }

    private Object createObject(String content, Map<String, Object> beans) {
        int index = content.indexOf("(");
        String className = content.substring(0, index);
        try {
            Class classOfInstance = Class.forName(className);
            Constructor constructor = classOfInstance.getConstructor();
            String argsString = content.substring(index + 1, content.indexOf(")"));
            String[] argStrings = argsString.split(",");
            Injectable object = (Injectable) constructor.newInstance(null);
            if(argStrings.length == 1 && argStrings[0].length() == 0) {
                return object;
            }

            Map<String, Object> args = new HashMap<>();
            for(int i = 0; i < argStrings.length; i++) {
                String argString = argStrings[i];
                String[] nameAndValue = argString.split(":");
                args.put(nameAndValue[0].trim(), beans.get(nameAndValue[1].trim()));
            }
            object.init(args);

            return object;
        } catch (ClassNotFoundException e) {
            ERRNO.write(R.string.class_not_fount);
        } catch (NoSuchMethodException e) {
            ERRNO.write(R.string.constructor_not_fount);
        } catch (IllegalAccessException e) {
            ERRNO.write(R.string.illegal_access);
        } catch (InstantiationException e) {
            ERRNO.write(R.string.could_not_create);
        } catch (InvocationTargetException e) {
            ERRNO.write(R.string.could_not_invoke);
        }
        return null;
    }

    private Object createMap(String content, Map<String, Object> beans) {
        content = content.substring(content.indexOf("{") + 1, content.indexOf("}")).trim();
        String[] items = content.split(",");
        Map<String, Object> result = new HashMap<>();
        if(items.length == 1 && items[0].length() == 0) return result;
        for(int i = 0; i < items.length; i++) {
            String[] nameAndValue = items[i].split(":");
            result.put(nameAndValue[0].trim(), beans.get(nameAndValue[1].trim()));
        }
        return result;
    }

    private Object createInteger(String content) {
        return Integer.parseInt(content);
    }


    private Map<String, Object> loadItems( Map<String, Object> beans) {
        Map<String, Object> result = new HashMap<>();
        String[] resItems= Res.array(R.array.injector_items);
        for(int i = 0; i < resItems.length; i++) {
            String[] nameAndValue = resItems[i].split(":");
            result.put(nameAndValue[0].trim(), beans.get(nameAndValue[1].trim()));
        }
        return result;
    }


}
