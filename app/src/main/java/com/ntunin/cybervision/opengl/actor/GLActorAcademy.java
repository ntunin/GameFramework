package com.ntunin.cybervision.opengl.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ntunin.cybervision.ercontext.Body;
import com.ntunin.cybervision.ercontext.World;
import com.ntunin.cybervision.opengl.motion.GLPitch;
import com.ntunin.cybervision.opengl.motion.GLRoll;
import com.ntunin.cybervision.opengl.motion.GLTransition;
import com.ntunin.cybervision.opengl.motion.GLTranslation;
import com.ntunin.cybervision.opengl.motion.GLYaw;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLActorAcademy {
    private World world;
    private GLDressingRoom glDressingRoom;

    public GLActorAcademy(World world, GLDressingRoom glDressingRoom) {
        this.world = world;
        this.glDressingRoom = glDressingRoom;
    }

    public GLActor get(Map<String, Object> description) {
        String id = (String) description.get("id");
        List<String> dresses = (List<String>) description.get("dresses");
        String firstDress = (String) description.get("first dress");
        Body b = world.getBody(id);
        return null;
    }

    private List<GLTransition> getStartTransitions() {
        List<GLTransition> result = new ArrayList<>();
        result.add(new GLTranslation());
        result.add(new GLYaw());
        result.add(new GLPitch());
        result.add(new GLRoll());
        return result;
    }
}
