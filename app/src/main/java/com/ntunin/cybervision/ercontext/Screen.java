package com.ntunin.cybervision.ercontext;

public abstract class Screen {

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
