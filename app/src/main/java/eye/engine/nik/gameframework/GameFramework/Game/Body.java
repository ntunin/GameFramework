package eye.engine.nik.gameframework.GameFramework.Game;

import eye.engine.nik.gameframework.GameFramework.Vector3;

/**
 * Created by nikolay on 12.10.16.
 */

public class Body {
    private float m;
    private Vector3 r;
    private Vector3 v;
    private Vector3 a;
    private float yaw, pitch, roll;
    private String id;

    public Body(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }

    public float getYaw() {
        return yaw;
    }

    public Vector3 getR() {
        return r;
    }
    public Vector3 getV() {
        return v;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setR(Vector3 r) {
        this.r = r;
    }


    public void setV(Vector3 v) {
        this.v = v;
    }
    public void addPitch(float pitch) {
        this.pitch += pitch;
    }

    public void addRoll(float roll) {
        this.roll += roll;
    }

    public void addYaw(float yaw) {
        this.yaw += yaw;
    }

    public void addR(Vector3 r) {
        this.r = this.r.add(r);
    }


    public void addV(Vector3 v) {
        this.v = this.v.add(v);
    }
}
