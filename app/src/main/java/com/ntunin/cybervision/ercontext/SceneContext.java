package com.ntunin.cybervision.ercontext;

import math.vector.Vector3;
import com.ntunin.cybervision.opengl.actor.GLActor;
import com.ntunin.cybervision.opengl.actor.GLActorAcademy;
import com.ntunin.cybervision.opengl.actor.GLDressingRoom;
import com.ntunin.cybervision.opengl.camera.Camera;
import com.ntunin.cybervision.opengl.camera.PerspectiveCamera;
import com.ntunin.cybervision.opengl.camera.PerspectiveRacurs;
import com.ntunin.cybervision.opengl.graphics.GLDress;
import com.ntunin.cybervision.opengl.light.GLAmbientLight;
import com.ntunin.cybervision.opengl.light.GLDirectionalLight;
import com.ntunin.cybervision.opengl.light.GLGaffer;
import com.ntunin.cybervision.opengl.light.GLLight;
import com.ntunin.cybervision.opengl.light.GLPointLight;
import com.ntunin.cybervision.opengl.motion.GLTransition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by nikolay on 12.10.16.
 */

public class SceneContext {
    private Map<String, Object> collection = new HashMap<>();
    private GLActorAcademy actorAcademy;

    public SceneContext() {
        Map<String, Camera> cameras = new HashMap<>();
        cameras.put("main camera", new PerspectiveCamera(new PerspectiveRacurs(new Vector3(0,0,0), new Vector3(0,0,-10))));
        Operator operator = new Operator(cameras);
        collection.put("operator", operator);
        World w = new World();
        w.addBody(new Body("weapons.kedr-1"));
        Map<String, String> framePathes = getFramesPathes();
        GLDressingRoom room = new GLDressingRoom();
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

    public void drawActors() {
        Map<String, GLActor> actors = (Map<String, GLActor>) get("actors");
        Set<String> actorNames = actors.keySet();
        for(String name: actorNames) {
            GLActor actor = actors.get(name);
            drawActor(actor);
        }
    }

    public void drawActor(GLActor actor) {
        Body b = actor.getBody();
        GLDress f = actor.getDress();
        List<GLTransition> transitions = actor.getTransitions();
        for(GLTransition t: transitions) {
            t.act(b);
        }
        f.draw();
    }

}
