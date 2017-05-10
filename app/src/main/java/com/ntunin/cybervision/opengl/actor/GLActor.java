package com.ntunin.cybervision.opengl.actor;

import java.util.ArrayList;
import java.util.List;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Body;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.injector.Injectable;
import com.ntunin.cybervision.opengl.graphics.GLDress;
import com.ntunin.cybervision.opengl.motion.GLTransition;
import com.ntunin.cybervision.res.ResMap;

/**
 * Created by nikolay on 17.10.16.
 */

public class GLActor implements Injectable{
    private List<GLTransition> transitions;
    private Body body;
    private GLDress dress;

    public GLActor(Body body, GLDress dress) {
        this.body = body;
        this.dress = dress;
    }

    public void setTransitions(List<GLTransition> transitions) {
        this.transitions = transitions;
    }

    public void play() {
        for(GLTransition t: transitions) {
            t.act(body);
        }
        dress.draw();
    }

    public GLDress getDress() {
        return dress;
    }

    public Body getBody() {
        return body;
    }

    public List<GLTransition> getTransitions() {
        return transitions;
    }

    @Override
    public void init(ResMap<String, Object> data) {

    }
}
