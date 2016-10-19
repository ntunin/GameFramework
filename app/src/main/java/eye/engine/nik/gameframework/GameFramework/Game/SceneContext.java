package eye.engine.nik.gameframework.GameFramework.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLActor.GLActor;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLActor.GLActorAcademy;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.GLActor.GLDressingRoom;
import eye.engine.nik.gameframework.GameFramework.Vector3;

/**
 * Created by nikolay on 12.10.16.
 */

public class SceneContext {
    private Map<String, Object> collection = new HashMap<>();
    private GLActorAcademy actorAcademy;

    public SceneContext() {
        Map<String, Camera> cameras = new HashMap<>();
        cameras.put("main camera", new PerspectiveCamera(new PerspectiveRacurs(new Vector3(0,0,0), new Vector3(0,0,10))));
        Operator operator = new Operator(cameras);
        collection.put("operator", operator);
        World w = new World();
        w.addBody(new Body("weapons.kedr-1"));
        Map<String, String> framePathes = getFramesPathes();
        GLDressingRoom room = new GLDressingRoom(framePathes);
        actorAcademy = new GLActorAcademy(w, room);
        Map<String, Object> kedr = getKedrDefinition();
        GLActor a = actorAcademy.get(kedr);
        Map<String, GLActor> actors = new HashMap<>();
        actors.put("actor.kedr-1", a);
        collection.put("actors", actors);
        collection.put("world", w);
    }
    private Map<String, String> getFramesPathes() {
        Map<String, String> result = new HashMap<>();
        result.put("frames.kedr-1", "cedr.X");
        return result;
    }
    private Map<String, Object> getKedrDefinition() {
        Map<String, Object> result = new HashMap<>();
        result.put("id", "weapons.kedr-1");
        List<String> dresses = new ArrayList<>();
        dresses.add("frames.kedr-1");
        result.put("dresses", dresses);
        result.put("first dress", "frames.kedr-1");
        return result;
    }
    public Object get(String setting) {
        return collection.get(setting);
    }

}
