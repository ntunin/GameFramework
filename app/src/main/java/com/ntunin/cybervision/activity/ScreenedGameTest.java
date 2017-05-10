package com.ntunin.cybervision.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Body;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.ercontext.Operator;
import com.ntunin.cybervision.ercontext.SceneContext;
import com.ntunin.cybervision.ercontext.Screen;
import com.ntunin.cybervision.ercontext.World;
import com.ntunin.cybervision.io.xfile.XFile;
import com.ntunin.cybervision.opengl.actor.GLActor;
import com.ntunin.cybervision.opengl.graphics.GLDress;
import com.ntunin.cybervision.opengl.graphics.GLGraphics;
import com.ntunin.cybervision.opengl.graphics.Graphics;
import com.ntunin.cybervision.opengl.light.GLGaffer;
import com.ntunin.cybervision.opengl.motion.GLTransition;
import com.ntunin.cybervision.opengl.screen.GLScreenView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by nik on 07.05.17.
 */

public class ScreenedGameTest extends ERContext {
    SceneContext context;
    GL10 gl;
    PowerManager.WakeLock wakeLock;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cvgl);
        PowerManager powerManager = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "GLGame");

    }

    private void setupCamera() {
//        Operator operator = (Operator) context.get("operator");
//        operator.shootFrom("main camera");
    }

    private void setupLight() {
//        GLGaffer gaffer = (GLGaffer) context.get("gaffer");
//        gaffer.enable("lights.ambient-1");
//        gaffer.enable("lights.point-1");
//        gaffer.enable("lights.directional-1");
    }




    private void drawActors() {
//        Map<String, GLActor> actors = (Map<String, GLActor>) context.get("actors");
//        Set<String> actorNames = actors.keySet();
//        for(String name: actorNames) {
//            GLActor actor = actors.get(name);
//            drawActor(actor);
//        }
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
    protected Screen getScreen() {
        return new Screen() {
            FloatBuffer vertices;;
            GLDress d;
            @Override
            public void update(float deltaTime) {
                World world = (World) context.get("world");
                world.update(deltaTime);
            }

            @Override
            public void present(float deltaTime) {
                gl.glClearColor(0, 0, 0, 1);
                gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
                gl.glEnable(GL10.GL_DEPTH_TEST);

                drawActors();
            }


            protected void clean(float r, float g, float b, float a) {
//                gl.glClearColor(r, g, b, a);
//                gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//                gl.glEnable(GL10.GL_DEPTH_TEST);

            }

            @Override
            public void pause() {

            }

            @Override
            public void resume() {
                context = new SceneContext();
                gl = (GL10) ERContext.get(R.string.gl);
//
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 2 * 4);
                byteBuffer.order(ByteOrder.nativeOrder());
                vertices = byteBuffer.asFloatBuffer();
                vertices.put( new float[] { 0.0f, 0.0f,
                        319.0f, 0.0f,
                        160.0f, 479.0f});
                vertices.flip();


                setupCamera();
               // gl.glColor4f(1, 0, 0, 1);

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
            public void dispose() {

            }
        };
    }
}
