package com.ntunin.cybervision.ercontext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import math.vector.Vector3;

/**
 * Created by nikolay on 17.10.16.
 */

public class World {
    Map<String, Body> bodies;
    public World() {
        bodies = new HashMap<>();
    }
    public void addBody(Body b) {
        String id = b.getId();
        b.setR(new Vector3(5, 5, 5));
        bodies.put(id, b);
    }
    public Body getBody(String id) {
        return bodies.get(id);
    }
    public void update(float delta) {
        Body b = bodies.get("weapons.kedr-1");
       // b.addYaw(delta * 100);
    }
    public void forEach(BodyHandler h) {
        Set<String> names = bodies.keySet();
        for(String name: names) {
            Body b = bodies.get(name);
            h.handle(b);
        }
    }
    public interface BodyHandler {
        public void handle(Body b);
    }
}
