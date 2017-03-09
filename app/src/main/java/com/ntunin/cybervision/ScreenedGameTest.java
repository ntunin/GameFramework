package com.ntunin.cybervision;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ntunin.cybervision.game.Body;
import com.ntunin.cybervision.game.Operator;
import com.ntunin.cybervision.game.SceneContext;
import com.ntunin.cybervision.game.World;
import com.ntunin.cybervision.graphics.GLDress;
import com.ntunin.cybervision.opengl.actor.GLActor;
import com.ntunin.cybervision.opengl.light.GLGaffer;
import com.ntunin.cybervision.opengl.motion.GLTransition;
import com.ntunin.cybervision.opengl.screen.GLScreenedGame;

/**
 * Created by mikhaildomrachev on 19/10/2016.
 */
public class ScreenedGameTest extends GLScreenedGame {
    SceneContext context;
    public ScreenedGameTest() {
        super();
        context = new SceneContext();
        setupCamera();
        setupLight();
    }

    private void setupCamera() {
        Operator operator = (Operator) context.get("operator");
        operator.shootFrom("main camera");
    }

    private void setupLight() {
        GLGaffer gaffer = (GLGaffer) context.get("gaffer");
        gaffer.enable("lights.ambient-1");
        gaffer.enable("lights.point-1");
        gaffer.enable("lights.directional-1");
    }

    @Override
    public void update(float deltaTime) {
        World world = (World) context.get("world");
        world.update(deltaTime);
    }
    @Override
    public void present(float deltaTime) {
        clean(0, 0, 0, 0);
        drawActors();
    }


    private void drawActors() {
        Map<String, GLActor> actors = (Map<String, GLActor>) context.get("actors");
        Set<String> actorNames = actors.keySet();
        for(String name: actorNames) {
            GLActor actor = actors.get(name);
            drawActor(actor);
        }
    }

    private void drawActor(GLActor actor) {
        Body b = actor.getBody();
        GLDress f = actor.getDress();
        List<GLTransition> transitions = actor.getTransitions();
        for(GLTransition t: transitions) {
            t.act(b);
        }
        f.draw();
    }
}
