package eye.engine.nik.gameframework.GameFramework.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.actor.GLActor;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.actor.GLActorAcademy;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.actor.GLDressingRoom;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.camera.Camera;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.camera.PerspectiveCamera;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.camera.PerspectiveRacurs;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.light.GLAmbientLight;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.light.GLDirectionalLight;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.light.GLGaffer;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.light.GLLight;
import eye.engine.nik.gameframework.GameFramework.Graphics.OpenGL.light.GLPointLight;
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
        Map<String, GLLight> lights = new HashMap<>();

        GLAmbientLight ambientLight = new GLAmbientLight();
        ambientLight.setColor(0, 0.2f, 0, 1);
        lights.put("lights.ambient-1", ambientLight);

        GLPointLight pointLight = new GLPointLight(0);
        pointLight.setDiffuse(1, 0, 0, 1);
        lights.put("lights.point-1", pointLight);


        GLDirectionalLight directionalLight = new GLDirectionalLight(1);
        directionalLight.setDiffuse(0, 0, 1, 1);
        directionalLight.setDirection(1, 0, 0);
        lights.put("lights.directional-1", directionalLight);

        GLGaffer gaffer = new GLGaffer(lights);
        collection.put("gaffer", gaffer);
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
