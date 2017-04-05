package com.ntunin.cybervision;

import com.ntunin.cybervision.injector.Injectable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by nikolay on 11.03.17.
 */

public class ObjectFactory extends ReleasableDelegate implements Injectable{
    private Map<String, List<Releasable>> releasedObjects;
    private Map<String, ReleasableFactory> factories;


    public void add(String tag, ReleasableFactory factory) {
        this.factories.put(tag, factory);
    }

    public Releasable get(String tag) {
        synchronized (this) {
            List<Releasable> releasedObjectsList = releasedObjects.get(tag);
            if(releasedObjectsList != null && releasedObjectsList.size() > 0) {
                Releasable object = releasedObjectsList.get(0);
                releasedObjectsList.remove(0);
                return object;
            } else {
                ReleasableFactory factory = factories.get(tag);
                Releasable object = factory.get();
                object.setDelegate(this);
                return object;
            }
        }
    }

    @Override
    public void release(Releasable object) {
        synchronized (this) {
            String tag = object.getTag();
            List<Releasable> releasedObjectsList = releasedObjects.get(tag);
            if(releasedObjectsList == null) {
                releasedObjectsList = new LinkedList<>();
            }
            releasedObjectsList.add(object);
            releasedObjects.put(tag, releasedObjectsList);
        }
    }

    @Override
    public void init(Map<String, Object> data) {
        Map factories = (Map) data.get("factories");
        this.factories = factories;
        if(this.factories == null) {
            this.factories = new HashMap<>();
        }
        this.releasedObjects = new HashMap<>();
    }
}
