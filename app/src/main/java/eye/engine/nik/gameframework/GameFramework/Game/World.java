package eye.engine.nik.gameframework.GameFramework.Game;

import java.util.HashMap;
import java.util.Map;

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
}
