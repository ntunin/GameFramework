package com.ntunin.cybervision.opengl.camera;


import math.vector.Vector3;

/**
 * Created by nikolay on 12.10.16.
 */

public class PerspectiveRacurs {
    public Vector3 position = new Vector3();
    public Vector3 target = new Vector3(0, 0, -1);
    public Vector3 up = new Vector3(0, 1, 0);
    public float angle = 63;
    public float near = 1;
    public float far = 1000;

    public PerspectiveRacurs(Vector3 target) {
        this.target = target;
    }

    public PerspectiveRacurs(Vector3 target, Vector3 position) {
        this.position = position;
        this.target = target;
    }

    public PerspectiveRacurs(Vector3 target,  float angle) {
        this.target = target;
        this.angle = angle;
    }
    public PerspectiveRacurs(Vector3 target, Vector3 position, float angle) {
        this.position = position;
        this.target = target;
        this.angle = angle;
    }
    public PerspectiveRacurs(Vector3 target,  float angle, float near, float far) {
        this.target = target;
        this.angle = angle;
        this.near = near;
        this.far = far;
    }
    public PerspectiveRacurs(Vector3 target, Vector3 position, float angle, float near, float far) {
        this.position = position;
        this.target = target;
        this.angle = angle;
        this.near = near;
        this.far = far;
    }
    public PerspectiveRacurs(Vector3 target, Vector3 position, float angle, float near, float far, Vector3 up) {
        this.position = position;
        this.target = target;
        this.angle = angle;
        this.near = near;
        this.far = far;
        this.up = up;
    }


}
