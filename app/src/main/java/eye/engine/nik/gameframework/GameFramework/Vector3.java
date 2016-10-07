package eye.engine.nik.gameframework.GameFramework;

/**
 * Created by nick on 01.03.16.
 */
import android.opengl.Matrix;
import android.util.FloatMath;

public class Vector3 {
    private static final float[] matrix = new float[16];
    private static final float[] inVec = new float[4];
    private static final float[] outVec = new float[4];
    public float x, y, z;

    public Vector3() {
    }
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }
    public Vector3 cpy() {
        return new Vector3(x, y, z);
    }
    public Vector3 set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    public Vector3 set(Vector3 other) {
        return this.set(other.x, other.y, other.z);
    }
    public Vector3 add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    public Vector3 add(Vector3 other) {
        return this.add(other.x, other.y, other.z);
    }
    public Vector3 sub(float x, float y, float z) {
        return this.add(-x, -y, -z);
    }
    public Vector3 sub(Vector3 other) {
        return this.sub(other.x, other.y, other.z);
    }
    public Vector3 mul(float scalar) {
        this.x *= scalar;
        return  this;
    }
    public float len() {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }
    public float lenSquared() {
        return x * x + y * y + z * z;
    }
    public Vector3 nor() {
        float len = len();
        if (len != 0) {
            this.x /= len;
            this.y /= len;
            this.z /= len;
        }
        return this;
    }
    public Vector3 rotate(float angle, float axisX, float axisY,
                          float axisZ) {
        inVec[0] = x;
        inVec[1] = y;
        inVec[2] = z;
        inVec[3] = 1;
        Matrix.setIdentityM(matrix, 0);
        Matrix.rotateM(matrix, 0, angle, axisX, axisY, axisZ);
        Matrix.multiplyMV(outVec, 0, matrix, 0, inVec, 0);
        x = outVec[0];
        y = outVec[1];
        z = outVec[2];
        return this;
    }
    public static Vector3 sub(Vector3 B, Vector3 A) {
        float x = B.x - A.x;
        float y = B.y - A.y;
        float z = B.z - A.z;
        return new Vector3(x, y, z);
    }
    public float dist(Vector3 other) {
        return sub(this, other).len();
    }
    public float dist(float x, float y, float z) {
        return  sub(this, new Vector3(x, y, z)).len();
    }
    public float distSquared(Vector3 other) {
        return sub(this, other).lenSquared();
    }
    public float distSquared(float x, float y, float z) {
        return sub(this, new Vector3(x, y, z)).lenSquared();
    }
}