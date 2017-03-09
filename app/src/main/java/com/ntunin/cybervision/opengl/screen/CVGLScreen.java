package com.ntunin.cybervision.opengl.screen;


import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import com.ntunin.cybervision.game.Body;
import com.ntunin.cybervision.game.Operator;
import com.ntunin.cybervision.game.SceneContext;
import com.ntunin.cybervision.game.Screen;
import com.ntunin.cybervision.game.World;
import com.ntunin.cybervision.graphics.GLDress;
import com.ntunin.cybervision.injector.Injector;
import com.ntunin.cybervision.injector.InternalInjector;
import com.ntunin.cybervision.journal.Journal;
import com.ntunin.cybervision.opengl.actor.GLActor;
import com.ntunin.cybervision.opengl.light.GLGaffer;
import com.ntunin.cybervision.opengl.motion.GLTransition;

/**
 * Created by nikolay on 25.01.17.
 */

public class CVGLScreen extends Screen {
    Injector injector;
    SceneContext context;
    Journal journal;
    protected GLGraphics glGraphics;
    protected CVGLGame glGame;

    public CVGLScreen() {
        super(CVGLGame.current());
        glGame = (CVGLGame)game;
        glGraphics = glGame.getGLGraphics();
        context = new SceneContext();
        injector = InternalInjector.main();
        journal = (Journal) injector.getInstance("Journal");
//        setupCamera();
//        setupLight();

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
    public void resume() {
        journal.start();
    }

    @Override
    public void update(float deltaTime) {
        World world = (World) context.get("world");
        world.update(deltaTime);
    }
    @Override
    public void present(float deltaTime) {
        clean();
        drawActors();
    }

    private void clean() {
        GL10 gl = glGraphics.getGL();
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL10.GL_DEPTH_TEST);
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

    @Override
    public void pause() {
        journal.stop();
    }

    @Override
    public void dispose() {

    }
}
