package com.ntunin.cybervision.activity;

import android.os.Handler;

import com.ntunin.cybervision.R;
import com.ntunin.cybervision.ercontext.Body;
import com.ntunin.cybervision.ercontext.ERContext;
import com.ntunin.cybervision.ercontext.SceneContext;
import com.ntunin.cybervision.opengl.actor.GLActor;
import com.ntunin.cybervision.opengl.actor.GLDressingRoom;
import com.ntunin.cybervision.opengl.graphics.GLDress;
import com.ntunin.cybervision.opengl.screen.HardSyncronizedGLScreen;
import com.ntunin.cybervision.res.ResMap;

/**
 * Created by nik on 29.04.17.
 */

public class MyGameScreen extends HardSyncronizedGLScreen {

    private ERStackViewTestActivity context;
    GLDressingRoom dressingRoom;
    GLActor kedr;
    Body kedrBody;


    public void setTestContext(ERStackViewTestActivity context) {
        this.context = context;
    }

    @Override
    public void resume() {
        super.resume();
        if(dressingRoom == null) {
            dressingRoom = (GLDressingRoom) ERContext.get(R.string.dressing_room);
        }
        dressingRoom.prepare();
        kedrBody = new Body("weapons.kedr-1");
        GLDress dress = dressingRoom.get("kedr");
        kedr = new GLActor(kedrBody, dress);
    }

    @Override
    public void updateAfterSync(float deltaTime) {
        kedrBody.addYaw(deltaTime*100);
    }

    @Override
    public void presentAfterSync(float deltaTime) {
        kedr.play();
    }

    @Override
    public void init(ResMap<String, Object> data) {
        super.init(data);
        dressingRoom = (GLDressingRoom) data.get(R.string.dressing_room);
    }
}
