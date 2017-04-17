package com.ntunin.cybervision.opengl.actor;

import java.util.ArrayList;
import java.util.List;

import com.ntunin.cybervision.ercontext.Body;
import com.ntunin.cybervision.opengl.graphics.GLDress;
import com.ntunin.cybervision.opengl.motion.GLTransition;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLActor {
    private List<GLTransition> transitions;
    private Body body;
    private GLDress dress;
    private List<String> dresses;
    private GLDressingRoom dressingRoom;

    public GLActor(Body body, GLDressingRoom dressingRoom) {
        dresses = new ArrayList<>();
        this.body = body;
        this.dressingRoom = dressingRoom;
    }

    public void setTransitions(List<GLTransition> transitions) {
        this.transitions = transitions;
    }

    public void play() {
        for(GLTransition t: transitions) {
            t.act(body);
        }
    }

    public GLDress getDress() {
        return dress;
    }

    public Body getBody() {
        return body;
    }

    public void setDresses(List<String> dresses) {
        this.dresses = dresses;
    }
    public void dress(String dressName) {
        this.dress = dressingRoom.get(dressName);
    }

    public List<GLTransition> getTransitions() {
        return transitions;
    }
}
