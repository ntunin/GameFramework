package com.ntunin.cybervision.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        bodies.put(id, b);
    }
    public Body getBody(String id) {
        return bodies.get(id);
    }
    public void update(float delta) {
        Body b = bodies.get("weapons.kedr-1");
        b.addYaw(10*delta);
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
